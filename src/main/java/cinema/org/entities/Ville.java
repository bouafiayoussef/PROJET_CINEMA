package cinema.org.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class Ville {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double longitude;
    private double latidude;
    private double altitude;
    @OneToMany(mappedBy="ville")
    private Collection<Cinema> cinemas;

}
