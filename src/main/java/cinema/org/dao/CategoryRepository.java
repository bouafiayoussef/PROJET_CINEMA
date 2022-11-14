package cinema.org.dao;

import cinema.org.entities.Categorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//la creation d'une APIRest pour chaque entite a l'aide de SpringDataRest (il suffit d'ajouter cette annotation)

@RepositoryRestResource   // ca veut dire que vous dites toutes le methodes que vous avez herite de JPA repository ils sont accessible via une API rest

public interface CategoryRepository extends JpaRepository<Categorie,Long> {
}
