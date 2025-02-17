package ec.edu.uce.pokedexweb.service;

import ec.edu.uce.pokedexweb.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExternalApiService {

    private final WebClient webClient;
    /**
     * Obtiene un Pokémon desde la API externa y lo mapea.
     */
    public Mono<Pokemon> getPokemonFromApi(String name) {
        return webClient.get()
                .uri("/pokemon/{name}", name.toLowerCase())
                .retrieve()
                .bodyToMono(Map.class)
                .map(this::mapToPokemon)
                .onErrorResume(e -> {
                    System.err.println("Error fetching Pokémon: " + name + " - " + e.getMessage());
                    return Mono.empty();
                });
    }

    /**
     * Obtiene todos los Pokémon en paralelo y los guarda en la base de datos en lotes.
     */
    public Flux<Pokemon> getAllPokemonFromApi(int limit, int offset) {
        return webClient.get()
                .uri("/pokemon?limit={limit}&offset={offset}", limit, offset)
                .retrieve()
                .bodyToMono(Map.class)
                .flatMapMany(data -> Flux.fromIterable((List<Map<String, Object>>) data.get("results")))
                .flatMap(result -> getPokemonFromApi((String) result.get("name"))
                        .subscribeOn(Schedulers.parallel())  // Procesar en paralelo
                )
                .onErrorResume(e -> {
                    System.err.println("Error fetching Pokémon list: " + e.getMessage());
                    return Flux.empty();
                });
    }

    /**
     * Mapea la respuesta de la API a un objeto Pokémon.
     */
    private Pokemon mapToPokemon(Map<String, Object> data) {
        if (data == null || !data.containsKey("id")) return null;

        Pokemon pokemon = new Pokemon();
        pokemon.setId((Integer) data.getOrDefault("id", 0));
        pokemon.setName((String) data.getOrDefault("name", "Unknown"));
        pokemon.setBaseExperience((Integer) data.getOrDefault("base_experience", 0));
        pokemon.setHeight((Integer) data.getOrDefault("height", 0));
        pokemon.setWeight((Integer) data.getOrDefault("weight", 0));
        pokemon.setOrderIndex((Integer) data.getOrDefault("order", 0));
        pokemon.setAbilities(mapList(data, "abilities", this::mapToAbility));
        pokemon.setStats(mapList(data, "stats", this::mapToStat));
        pokemon.setTypes(mapList(data, "types", this::mapToType));
        pokemon.setMoves(mapList(data, "moves", this::mapToMove));
        pokemon.setSprites(mapToSprites((Map<String, Object>) data.getOrDefault("sprites", Map.of())));
        return pokemon;
    }

    private <T> List<T> mapList(Map<String, Object> data, String key, java.util.function.Function<Map<String, Object>, T> mapper) {
        return ((List<Map<String, Object>>) data.getOrDefault(key, List.of()))
                .stream().map(mapper).toList();
    }

    private Ability mapToAbility(Map<String, Object> data) {
        Map<String, Object> abilityData = (Map<String, Object>) data.get("ability");
        return new Ability(
                (String) abilityData.getOrDefault("name", "Unknown"),
                (String) abilityData.getOrDefault("url", ""),
                (Boolean) data.getOrDefault("is_hidden", false),
                (Integer) data.getOrDefault("slot", 0)
        );
    }

    private Stat mapToStat(Map<String, Object> data) {
        Map<String, Object> statData = (Map<String, Object>) data.get("stat");
        return new Stat(
                (Integer) data.getOrDefault("base_stat", 0),
                (Integer) data.getOrDefault("effort", 0),
                (String) statData.getOrDefault("name", "Unknown")
        );
    }

    private Type mapToType(Map<String, Object> data) {
        Map<String, Object> typeData = (Map<String, Object>) data.get("type");
        return new Type(
                (Integer) data.getOrDefault("slot", 0),
                (String) typeData.getOrDefault("name", "Unknown"),
                (String) typeData.getOrDefault("url", "")
        );
    }

    private Move mapToMove(Map<String, Object> data) {
        Map<String, Object> moveData = (Map<String, Object>) data.get("move");
        return new Move(
                (String) moveData.getOrDefault("name", "Unknown"),
                (String) moveData.getOrDefault("url", "")
        );
    }

    /**
     * Mapea los sprites de la API al objeto Sprites.
     */
    private Sprites mapToSprites(Map<String, Object> data) {
        Sprites sprites = new Sprites();
        sprites.setFrontDefault((String) data.getOrDefault("front_default", ""));
        sprites.setBackDefault((String) data.getOrDefault("back_default", ""));
        sprites.setFrontShiny((String) data.getOrDefault("front_shiny", ""));
        sprites.setBackShiny((String) data.getOrDefault("back_shiny", ""));
        sprites.setFrontFemale((String) data.getOrDefault("front_female", ""));
        sprites.setBackFemale((String) data.getOrDefault("back_female", ""));
        sprites.setFrontShinyFemale((String) data.getOrDefault("front_shiny_female", ""));
        sprites.setBackShinyFemale((String) data.getOrDefault("back_shiny_female", ""));

        Optional.ofNullable((Map<String, Object>) data.get("other"))
                .ifPresent(otherSprites -> {
                    Optional.ofNullable((Map<String, Object>) otherSprites.get("dream_world"))
                            .ifPresent(dreamWorld -> sprites.setDreamWorldFront((String) dreamWorld.getOrDefault("front_default", "")));

                    Optional.ofNullable((Map<String, Object>) otherSprites.get("official-artwork"))
                            .ifPresent(artwork -> sprites.setOfficialArtworkFront((String) artwork.getOrDefault("front_default", "")));
                });

        return sprites;
    }
}
