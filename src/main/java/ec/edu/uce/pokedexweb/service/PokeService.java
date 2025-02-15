package ec.edu.uce.pokedexweb.service;

import ec.edu.uce.pokedexweb.models.Pokemon;
import ec.edu.uce.pokedexweb.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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
     * Busca un Pokémon por su nombre de forma reactiva.
     *
     * @param name Nombre del Pokémon.
     * @return Mono con `ResponseEntity<PokemonDto>`.
     */
    public Mono<ResponseEntity<Pokemon>> getPokemonByName(String name) {
        return Mono.fromCallable(() -> pokemonRepository.findByNameIgnoreCase(name)) // Obtiene el Pokémon de la BD
                .map(Optional::orElseThrow) // Lanza excepción si no lo encuentra
                .map(ResponseEntity::ok) // Retorna 200 OK si existe
                .defaultIfEmpty(ResponseEntity.notFound().build()) // Retorna 404 si no lo encuentra
                .subscribeOn(Schedulers.boundedElastic()); // Optimización de hilos
    }
}
