package ec.edu.uce.pokedexweb.service;

import ec.edu.uce.pokedexweb.models.Pokemon;
import ec.edu.uce.pokedexweb.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PokeService {

    private final PokemonRepository pokemonRepository;
    private final ExternalApiService externalApiService;

    /**
     * Carga Pokémon desde la API externa y los guarda en la base de datos.
     *
     * @param limit Número de Pokémon a obtener.
     * @param offset Punto de inicio para la carga.
     */
    @Transactional
    public Mono<Void> loadAllPokemonsFromApiAndSave(int limit, int offset) {
        return externalApiService.getAllPokemonFromApi(limit, offset)
                .flatMap(this::savePokemonData) // Procesamiento reactivo
                .then(); // Finaliza cuando todos los datos se han procesado
    }

    /**
     * Guarda un Pokémon en la base de datos.
     *
     * @param pokemon Objeto de tipo `Pokemon` a guardar.
     * @return `Mono<Pokemon>` indicando el resultado de la operación.
     */
    @Transactional
    public Mono<Pokemon> savePokemonData(Pokemon pokemon) {
        return Mono.fromCallable(() -> pokemonRepository.save(pokemon))
                .subscribeOn(reactor.core.scheduler.Schedulers.boundedElastic()); // Optimización de hilos
    }

    /**
     * Busca un Pokémon por nombre.
     */
    public Optional<Pokemon> getPokemonByName(String name) {
        return pokemonRepository.findByNameIgnoreCase(name);
    }
}
