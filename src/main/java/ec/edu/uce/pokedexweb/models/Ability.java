package ec.edu.uce.pokedexweb.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremental para la tabla
    private Long id;

    private String name;
    private String url;
    private boolean isHidden;
    private int slot;

    @ManyToOne(fetch = FetchType.LAZY) // Muchas habilidades pueden pertenecer a un Pokémon
    @JoinColumn(name = "pokemon_id") // Clave foránea en Ability
    @JsonBackReference
    private Pokemon pokemon;

    public Ability(String name, String url, boolean isHidden, int slot) {
        this.name = name;
        this.url = url;
        this.isHidden = isHidden;
        this.slot = slot;
    }

}
