package ec.edu.uce.pokedexweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Clave primaria auto-generada
    private Long id;

    private String name;
    private String url;

    @ManyToOne(fetch = FetchType.EAGER) // Muchos movimientos pertenecen a un Pokémon
    @JoinColumn(name = "pokemon_id") // Clave foránea en Move
    @JsonIgnore // 👈 Evita la recursión infinita
    private Pokemon pokemon;

    public Move(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
