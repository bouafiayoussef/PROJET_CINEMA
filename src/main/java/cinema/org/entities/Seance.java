package cinema.org.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
// la seance juste un planning mais la duree de film il est dans le film , la date de projection dans la projection
// on va les utiliser dans le projection (la date dans la projection) mais l'heure dans la seance
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Seance {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIME)      // si vous voulez specifier le format de la date (on specifier juste l'heure car la date dans la projection )
    private Date dateDebut;
}
