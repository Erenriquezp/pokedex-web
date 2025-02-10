package ec.edu.uce.pokedexweb.service;

import ec.edu.uce.pokedexweb.models.Sprites;
import ec.edu.uce.pokedexweb.repository.SpritesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpriteService {

    private final SpritesRepository spritesRepository;
    private final ExternalApiService externalApiService;

    /**
     * Obtiene los sprites de un Pokémon desde la base de datos o la API externa si no están almacenados.
     *
     * @param pokemonName Nombre del Pokémon.
     * @return Mono con los sprites del Pokémon.
     */
    public Mono<Sprites> getSpritesForPokemon(String pokemonName) {
        return Mono.justOrEmpty(spritesRepository.findByPokemonNameIgnoreCase(pokemonName))
                .switchIfEmpty(fetchAndSaveSprites(pokemonName));
    }

    /**
     * Si no hay sprites en la base de datos, los obtiene de la API y los guarda.
     *
     * @param pokemonName Nombre del Pokémon.
     * @return Mono con los sprites obtenidos y guardados.
     */
    private Mono<Sprites> fetchAndSaveSprites(String pokemonName) {
        return externalApiService.getPokemonFromApi(pokemonName)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(pokemon -> {
                    Sprites fetchedSprites = Optional.ofNullable(pokemon.getSprites()).orElse(new Sprites());
                    fetchedSprites.setPokemon(pokemon);
                    spritesRepository.save(fetchedSprites);
                    return Mono.just(fetchedSprites);
                })
                .onErrorResume(e -> {
                    System.err.println("Error al obtener sprites para " + pokemonName + ": " + e.getMessage());
                    return Mono.empty();
                });
    }
}
