package ec.edu.uce.pokedexweb.controller;

import ec.edu.uce.pokedexweb.models.Sprites;
import ec.edu.uce.pokedexweb.service.SpriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para obtener los sprites de un Pokémon.
 */
@RestController
@RequestMapping("/api/sprites")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permitir peticiones desde cualquier origen
public class SpriteController {

    private final SpriteService spriteService;

    /**
     * Obtiene los sprites de un Pokémon por su nombre.
     *
     * @param pokemonName Nombre del Pokémon.
     * @return Mono con `ResponseEntity<Sprites>`, retorna `404` si no se encuentra.
     */
    @GetMapping("/{pokemonName}")
    public Mono<ResponseEntity<Sprites>> getSpritesForPokemon(@PathVariable String pokemonName) {
        return spriteService.getSpritesForPokemon(pokemonName)
                .map(ResponseEntity::ok) // Retorna 200 OK con los sprites
                .defaultIfEmpty(ResponseEntity.notFound().build()); // Retorna 404 si no se encuentra
    }
}
