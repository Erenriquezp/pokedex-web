package ec.edu.uce.pokedexweb.controller;

import ec.edu.uce.pokedexweb.models.Pokemon;
import ec.edu.uce.pokedexweb.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * Controlador REST para obtener Pokémon por tipo.
 */
@RestController
@RequestMapping("/api/types")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier frontend
public class TypeController {

    private final TypeService typeService;

    /**
     * Obtiene todos los Pokémon asociados a un tipo.
     *
     * @param typeName Nombre del tipo de Pokémon (ej. "fire", "water").
     * @return Lista de Pokémon en un `Flux`.
     */
    @GetMapping("/{typeName}")
    public ResponseEntity<Flux<Pokemon>> getPokemonByType(@PathVariable String typeName) {
        return ResponseEntity.ok().body(typeService.getPokemonByType(typeName));
    }
}
