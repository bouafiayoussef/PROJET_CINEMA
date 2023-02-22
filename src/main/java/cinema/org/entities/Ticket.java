package cinema.org.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nomClient;
    private  double prix;
    @Column(unique = false , nullable = true)  // il est unique, mais il accepte la valeur null // pas unique hh pour le test d'acheter plusieurs tickets à la fois
    private Integer codePayement;  // int valeur par default = 0, integer valeur par default null
    private boolean reservee;
    @ManyToOne
    private Place place;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    private Projection projection;
}
