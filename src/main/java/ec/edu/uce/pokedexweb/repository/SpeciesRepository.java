package ec.edu.uce.pokedexweb.repository;

import ec.edu.uce.pokedexweb.models.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {
    // Buscar especie por nombre
    Species findByName(String name);
}
