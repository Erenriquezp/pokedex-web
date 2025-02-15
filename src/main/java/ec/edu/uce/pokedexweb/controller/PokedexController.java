package ec.edu.uce.pokedexweb.controller;

import ec.edu.uce.pokedexweb.dto.PokemonDto;
import ec.edu.uce.pokedexweb.models.Pokemon;
import ec.edu.uce.pokedexweb.service.PokeService;
import ec.edu.uce.pokedexweb.service.PokeServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Controlador REST para la gestión de Pokémon.
 * Proporciona endpoints para consultar Pokémon por nombre y paginación.
 */
@RestController
@RequestMapping("/api/pokemon")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite solicitudes desde cualquier origen
public class PokedexController {

    private final PokeServiceDto pokeServiceDto;
    private final PokeService pokeService;

    /**
     * Obtiene una lista paginada de Pokémon en formato DTO.
     *
     * @param page Número de página (por defecto 0).
     * @param size Cantidad de elementos por página (por defecto 20).
     * @return Lista de Pokémon paginados.
     */
    @GetMapping
    public ResponseEntity<List<PokemonDto>> getAllPokemon(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        List<PokemonDto> pokemons = pokeServiceDto.getPokemonPage(page, size);

        if (pokemons.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content si no hay datos
        }
        return ResponseEntity.ok(pokemons); // 200 OK con la lista de Pokémon
    }

    /**
     * Obtiene un Pokémon por su nombre.
     *
     * @param name Nombre del Pokémon.
     * @return `Mono<ResponseEntity<PokemonDto>>` con los datos del Pokémon o 404 si no se encuentra.
     */
    @GetMapping("/{name}")
    public Mono<ResponseEntity<PokemonDto>> getPokemonByName(@PathVariable String name) {
        return pokeServiceDto.getPokemonByName(name);
    }

    /**
     * Obtiene un Pokémon por su nombre.
     *
     * @param name Nombre del Pokémon.
     * @return `Mono<ResponseEntity<PokemonDto>>` con los datos del Pokémon o 404 si no se encuentra.
     */
    @GetMapping("name/{name}")
    public Mono<ResponseEntity<Pokemon>> getPokemonByName1(@PathVariable String name) {
        return pokeService.getPokemonByName(name);
    }

    /**
     * Carga todos los Pokémon desde la API externa y los guarda en la base de datos.
     *
     * @return Respuesta indicando que la operación fue aceptada y se está procesando.
     */
    @PostMapping("/load")
    public ResponseEntity<String> loadAllPokemonData() {
        int totalPokemon = 1025; // Se puede cambiar dinámicamente si es necesario
        int offset = 0;

        pokeService.loadAllPokemonsFromApiAndSave(totalPokemon, offset)
                .subscribe(); // Ejecutar en segundo plano sin bloquear

        return ResponseEntity.accepted().body("Pokemon data is being loaded in the background.");
    }
}
