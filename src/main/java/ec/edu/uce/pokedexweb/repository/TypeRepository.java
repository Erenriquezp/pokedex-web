package ec.edu.uce.pokedexweb.repository;

import ec.edu.uce.pokedexweb.models.Pokemon;
import ec.edu.uce.pokedexweb.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    /**
     * Buscar todos los Pokémon asociados a un tipo.
     *
     * @param typeName El nombre del tipo (por ejemplo, "fire").
     * @return Lista de Pokémon asociados a ese tipo.
     */
    @Query("SELECT DISTINCT p FROM Pokemon p JOIN p.types t WHERE LOWER(t.name) = LOWER(:typeName)")
    List<Pokemon> findPokemonsByTypeName(String typeName);
}
