package cinema.org.dao;

import cinema.org.entities.Categorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//la creation d'une APIRest pour chaque entite a l'aide de SpringDataRest (il suffit d'ajouter cette annotation)

@RepositoryRestResource   // ca veut dire que vous dites toutes le methodes que vous avez herité de JPA repository ils sont accessible via une API rest
//http://localhost:8080/categories?page=0&size=2&sort=name,desc
//spring data rest il fait la pagination par ce que ce n'a pas de sens de retourner une grande quantité de donnes
// quand vous lui dites les categories ou les projectios , il vous donne que les 20 premiere et à a la fain il vous dit combien de size et le nombre du page .
public interface CategoryRepository extends JpaRepository<Categorie,Long> {
}
