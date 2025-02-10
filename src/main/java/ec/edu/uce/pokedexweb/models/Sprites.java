package ec.edu.uce.pokedexweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Sprites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Clave primaria auto-generada
    private Long id;

    private String frontDefault;
    private String backDefault;
    private String frontShiny;
    private String backShiny;
    private String frontFemale;
    private String backFemale;
    private String frontShinyFemale;
    private String backShinyFemale;

    @OneToOne(mappedBy = "sprites") // Relación inversa con Pokémon
    @JsonIgnore
    private Pokemon pokemon;
}
