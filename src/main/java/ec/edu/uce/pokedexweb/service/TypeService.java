package ec.edu.uce.pokedexweb.service;

import ec.edu.uce.pokedexweb.models.Pokemon;
import ec.edu.uce.pokedexweb.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;

    /**
     * Obtiene todos los Pokémon asociados a un tipo.
     *
     * @param typeName Nombre del tipo de Pokémon (ej. "fire", "water").
     * @return Flux con la lista de Pokémon asociados al tipo.
     */
    public Flux<Pokemon> getPokemonByType(String typeName) {
        return Flux.defer(() -> Flux.fromIterable(typeRepository.findPokemonsByTypeName(typeName)))
                .subscribeOn(Schedulers.boundedElastic()) // Optimiza para consultas bloqueantes
                .onErrorResume(e -> {
                    System.err.println("Error obteniendo Pokémon del tipo " + typeName + ": " + e.getMessage());
                    return Flux.empty();
                });
    }
}
