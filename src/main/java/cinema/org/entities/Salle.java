package cinema.org.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Salle {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int nombrePlaces;
    @ManyToOne
    private Cinema cinema ;
    @OneToMany(mappedBy = "salle")
    private Collection<Place> places;

    @OneToMany(mappedBy="salle")
    private Collection<Projection> projections;
}
