package ec.edu.uce.pokedexweb.service;

import ec.edu.uce.pokedexweb.models.Stat;
import ec.edu.uce.pokedexweb.repository.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class StatService {

    private final StatRepository statRepository;

    /**
     * Obtiene las estadísticas de un Pokémon desde la base de datos.
     *
     * @param pokemonName Nombre del Pokémon.
     * @return Flux con las estadísticas del Pokémon.
     */
    public Flux<Stat> getStatsForPokemon(String pokemonName) {
        return Flux.fromIterable(statRepository.findByPokemonName(pokemonName))
                .onErrorResume(e -> {
                    System.err.println("Error obteniendo estadísticas para " + pokemonName + ": " + e.getMessage());
                    return Flux.empty();
                });
    }
}
