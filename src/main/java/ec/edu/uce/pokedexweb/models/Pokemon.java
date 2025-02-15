package ec.edu.uce.pokedexweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Pokemon {

    @Id
    private int id; // Id único del Pokémon

    private String name;
    private Integer baseExperience; // Cambiado a Integer para permitir null
    private Integer height; // Cambiado a Integer para permitir null
    private Integer weight; // Cambiado a Integer para permitir null

    @Column(name = "pokemon_order") // Cambia el nombre de la columna
    private Integer orderIndex; // Cambiado a Integer para permitir null

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "pokemon_id") // Clave foránea en Ability
    @JsonManagedReference // Ignora esta propiedad al serializar
    private List<Ability> abilities;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "pokemon_id") // Clave foránea en Stat
    @JsonIgnore // Ignora esta propiedad al serializar
    private List<Stat> stats;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "pokemon_id") // Clave foránea en Type
    @JsonManagedReference // 👈 Controla la serialización de la relación
    private List<Type> types;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "pokemon_id") // Clave foránea en Move
    private List<Move> moves;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "species_id") // Clave foránea en Species
    private Species species;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sprites_id") // Clave foránea en Sprites
    private Sprites sprites;
}