package ec.edu.uce.pokedexweb.controller;

import ec.edu.uce.pokedexweb.service.EvolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para manejar las cadenas evolutivas de Pokémon.
 */
@RestController
@RequestMapping("/api/evolution")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier dominio
public class EvolutionController {

    private final EvolutionService evolutionService;

    /**
     * Obtiene la cadena evolutiva de un Pokémon por su nombre de especie.
     *
     * @param speciesName Nombre de la especie del Pokémon.
     * @return Lista de etapas evolutivas.
     */
    @GetMapping("/{speciesName}")
    public Mono<ResponseEntity<?>> getEvolutionChain(@PathVariable String speciesName) {
        return evolutionService.getEvolutionChain(speciesName)
                .map(evolutionChain -> evolutionChain.isEmpty()
                        ? ResponseEntity.noContent().build() // 204 No Content si no hay evolución
                        : ResponseEntity.ok(evolutionChain)) // 200 OK si hay evolución
                .defaultIfEmpty(ResponseEntity.notFound().build()); // 404 Not Found si no se encuentra
    }
}
