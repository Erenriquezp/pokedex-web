package ec.edu.uce.pokedexweb.controller;

import ec.edu.uce.pokedexweb.models.Stat;
import ec.edu.uce.pokedexweb.service.StatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * Controlador REST para obtener estadísticas de Pokémon.
 */
@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier frontend
public class StatController {

    private final StatService statService;

    /**
     * Obtiene las estadísticas de un Pokémon por su nombre.
     *
     * @param pokemonName Nombre del Pokémon.
     * @return Flux con las estadísticas o un error si no se encuentran.
     */
    @GetMapping("/{pokemonName}")
    public ResponseEntity<Flux<Stat>> getStatsForPokemon(@PathVariable String pokemonName) {
        Flux<Stat> stats = statService.getStatsForPokemon(pokemonName);
        return ResponseEntity.ok(stats);
    }
}
