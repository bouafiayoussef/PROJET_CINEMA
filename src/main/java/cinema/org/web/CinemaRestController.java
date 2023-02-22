package cinema.org.web;
// la premiere solution étaiti de mettre les photo dans un dossier sur le dossier static
//pour pouvoir Accéder à ces photos j'ai besoin de connaitre que le id de le film (identifiant) ,  si je connais le id du film je peux consulter la photo
// la troisieme solution c'est de stocker la phtot dans la base de donnes , en stockent la phtot en format string prcq tout le contenu d'un photo on peut la convertir en format string et vous pouvez l'enregistrer
// dans ce cas là , la phtot devient comme l'un des champs de l'entité , mais on les utulise uniquement quand vous avez la quantité de photos si les pohtos que vous gérer ne sont pas trés volumineuse
// pcq quand vous gérez beaucoup de ohtots qui sont trés volumineuse vous risquez d'avoir une base de donnes qui'a assez lourd

import cinema.org.dao.FilmRepository;
import cinema.org.dao.TicketRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import cinema.org.entities.*;
import lombok.Data;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController

// on va créer un Rest controller, si vous voulez créer une APi sans passer par SpringDataRest, il faut utiliser @RestController
// au lieu d'utiliser Controller, vous utilisez RestController

public class CinemaRestController {

    // 1 on n'a pas besoin de faire ça par ce qu'on a déjà la solution (spring data rest)
 /*   @Autowired
    private FilmRepository filmRepository;

   @GetMapping("/listFilm")
    public List<Film> films(){
      return filmRepository.findAll();
      // la solution de cette erreur (la boucle infinie des donnes) est de mettre ça dans les champs qui on n'a pas besoin dans l'affichage
        //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
 } */
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;
    // 2, mais plutôt on va l'utiliser uniquement pour d'autres méthodes par exemple acheter des tickets et également pour consulter l'image

    // (comment consulter une image) vous savez qu'une image, ce sont des données binaires

    @GetMapping(path="/imageFilm/{id}",produces=MediaType.IMAGE_JPEG_VALUE)   // produces juste pour le navigateur, car il représente les donnes octet par une image (produces par défaut prend APPLICATION_JSON)
    public byte[] image(@PathVariable(name="id") Long id) throws Exception{  // PathVar pour récupérer l'id
      Film f =filmRepository.findById(id).get();
      String photoName=f.getPhoto();
      File file =new File(System.getProperty("user.home")+"/Bureau Youssef/testBack/image/"+photoName+".jpg");  //le contenu de la photo se trouve dans le bureau, pour cela il faut utiliser un truc comme File
        Path path= Paths.get(file.toURI());
          return Files.readAllBytes(path); // readAllBytes retourne un tableau d'octet, pour cela il lui faut donner objet de type path
    }  // je viens de vous montre là avec l'image, vous pouvez le faire pour le fichier PDF ou WORD pour n'importe quelle forme, la seule chose que vous allez changer MediaType.APPLICATION_PDF_VALUE


  // dans notre controller nous allons ajouter une autre méthode qui permet de payer un ticket
  @PostMapping("/payerTickets")

  public List<Ticket> payerTicket(@RequestBody TicketForm ticketform) throws Exception{
        List<Ticket> listTicket = new ArrayList<>();
        ticketform.getTickets().forEach(idTicket->{
          Ticket ticket = ticketRepository.findById(idTicket).get();
          ticket.setNomClient(ticketform.getNomClient());
          ticket.setReservee(true);
          ticket.setCodePayement(ticketform.getCodePayment());
         ticketRepository.save(ticket);
            listTicket.add(ticket);
      });
    return  listTicket;
    // alors en principe ça devrait marcher à juste un petit problème qui va rencontrer lequel le problème rappelez-vous de la boucle infinie
  }

}
@Data // pour getters et les setters
class TicketForm{
    private String nomClient;
    private List<Long> tickets = new ArrayList<>();
    private Integer codePayment;
}





















