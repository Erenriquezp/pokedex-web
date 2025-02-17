package ec.edu.uce.pokedexweb.repository;

import ec.edu.uce.pokedexweb.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
    // Buscar un Pokémon por nombre (evita NullPointerException)
    Optional<Pokemon> findByNameIgnoreCase(String name);

}
