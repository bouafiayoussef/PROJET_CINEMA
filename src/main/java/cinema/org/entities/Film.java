package cinema.org.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String decription;
    private String realisateur;
    private Date dateSortie;
    private double duree;
    private String photo;
    @OneToMany(mappedBy = "film")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // on a pas besoin d'afficher la projection + l'erreur de boucle infine => un film se fait dans une projection , une projection contient un film , se film projecter dans une projection
    private Collection<Projection> projections;
    @ManyToOne
    private Categorie categorie;
}