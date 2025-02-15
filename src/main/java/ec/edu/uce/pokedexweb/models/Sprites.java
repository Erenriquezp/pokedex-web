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

    // Sprites de "Other"
    private String dreamWorldFront;
    private String dreamWorldFrontFemale;

    private String homeFront;
    private String homeFrontShiny;
    private String homeFrontFemale;
    private String homeFrontShinyFemale;

    private String officialArtworkFront;
    private String officialArtworkShiny;

    @OneToOne(mappedBy = "sprites") // Relación inversa con Pokémon
    @JsonIgnore
    private Pokemon pokemon;
}
