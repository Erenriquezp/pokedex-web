package ec.edu.uce.pokedexweb.repository;

import ec.edu.uce.pokedexweb.models.Sprites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpritesRepository extends JpaRepository<Sprites, Long> {
    // Agregar consultas personalizadas si es necesario
    Optional<Sprites> findByPokemonNameIgnoreCase(String pokemonName);
}
