package ec.edu.uce.pokedexweb.dto;

import ec.edu.uce.pokedexweb.models.Ability;
import ec.edu.uce.pokedexweb.models.Type;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PokemonDto {
    private String name;
    private List<Type> types;
    private List<Ability> abilities;
    private String spriteUrl;
}
