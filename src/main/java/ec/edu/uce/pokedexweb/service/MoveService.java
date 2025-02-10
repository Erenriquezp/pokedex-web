package ec.edu.uce.pokedexweb.service;

import ec.edu.uce.pokedexweb.models.Move;
import ec.edu.uce.pokedexweb.repository.MoveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoveService {

    private final MoveRepository moveRepository;

    public MoveService(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    /**
     * Retrieves all moves for a Pokémon from the database.
     *
     * @param pokemonName Name of the Pokémon.
     * @return List of Move objects.
     */
    public List<Move> getMovesForPokemon(String pokemonName) {
        return moveRepository.findByPokemonName(pokemonName);
    }
}
