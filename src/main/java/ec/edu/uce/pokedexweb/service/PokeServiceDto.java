package ec.edu.uce.pokedexweb.service;

import ec.edu.uce.pokedexweb.dto.PokemonDto;
import ec.edu.uce.pokedexweb.models.*;
import ec.edu.uce.pokedexweb.repository.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de Pokémon con optimización en cache y paginación.
 */
@Service
@RequiredArgsConstructor
public class PokeServiceDto {

    private final PokemonRepository pokemonRepository;

    /**
     * Convierte una entidad `Pokemon` en un objeto `PokemonDto`.
     *
     * @param pokemon La entidad `Pokemon` a mapear.
     * @return Un objeto `PokemonDto`.
     */
    private PokemonDto mapToPokemonDto(Pokemon pokemon) {
        if (pokemon == null) {
            return null; // Evita NullPointerException
        }

        return PokemonDto.builder()
                .name(pokemon.getName())
                .spriteUrl(pokemon.getSprites() != null ? pokemon.getSprites().getFrontDefault() : "")
                .types(pokemon.getTypes() != null ?
                        pokemon.getTypes().stream()
                                .map(type -> new Type(type.getSlot(), type.getName(), type.getUrl()))
                                .collect(Collectors.toList()) : Collections.emptyList())
                .abilities(pokemon.getAbilities() != null ?
                        pokemon.getAbilities().stream()
                                .map(ability -> new Ability(ability.getName(), ability.getUrl(), ability.isHidden(), ability.getSlot()))
                                .collect(Collectors.toList()) : Collections.emptyList())
                .build();
    }

    /**
     * Obtiene una lista paginada de Pokémon en formato DTO.
     *
     * @param offset Número de página.
     * @param limit  Cantidad de elementos por página.
     * @return Lista de `PokemonDto`.
     */
    @Cacheable(value = "pokemons", key = "#offset + '-' + #limit") // Optimiza el caché con una clave dinámica
    public List<PokemonDto> getPokemonPage(int offset, int limit) {
        Page<PokemonDto> pokemonPage = pokemonRepository.findAll(PageRequest.of(offset, limit))
                .map(this::mapToPokemonDto);

        return pokemonPage.hasContent() ? pokemonPage.getContent() : Collections.emptyList();
    }


    /**
     * Busca un Pokémon por su nombre de forma reactiva.
     *
     * @param name Nombre del Pokémon.
     * @return Mono con `ResponseEntity<PokemonDto>`.
     */
    public Mono<ResponseEntity<PokemonDto>> getPokemonByName(String name) {
        return Mono.fromCallable(() -> pokemonRepository.findByNameIgnoreCase(name)) // Obtiene el Pokémon de la BD
                .map(Optional::orElseThrow) // Lanza excepción si no lo encuentra
                .map(this::mapToPokemonDto) // Mapea a DTO
                .map(ResponseEntity::ok) // Retorna 200 OK si existe
                .defaultIfEmpty(ResponseEntity.notFound().build()) // Retorna 404 si no lo encuentra
                .subscribeOn(Schedulers.boundedElastic()); // Optimización de hilos
    }

}
