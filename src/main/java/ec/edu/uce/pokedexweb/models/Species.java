package ec.edu.uce.pokedexweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Species {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremental
    private Long id;

    private String name;
    private String url;

    @OneToOne(mappedBy = "species") // Relación inversa con Pokémon
    @JsonIgnore
    private Pokemon pokemon;
}
