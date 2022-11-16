package cinema.org.service;

import cinema.org.dao.*;
import cinema.org.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.PrivateKey;
import java.text.*;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service // on a fait cette annotation pour dire a spring que cette classe s'agit d'un service
@Transactional //toutes les methodes sont transactionnel (commit et rollback)
// pour n'a pas repete @Transactional dans chaque methode , je la mis au niveau de la classe , ca veut dire que tout les classes sont transactionnel
public class CinemaInitServiceImpl implements ICinemaInitService{

    @Autowired // ordonner a spring de m'injecter une implementation de VilleRepository grace a la nottation @Autowaired (injection de depandances)
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private  ProjectionRepository projectionRepository;

    @Override
    public void initVilles() {
        Stream.of("Casablanca","Marrakech","Rabat","Tanger").forEach(nameVille->{
            Ville ville = new Ville();
            ville.setName(nameVille);
            villeRepository.save(ville);
        });
    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(v->{
            Stream.of("Megarama","IMAX","FOUNOUN","CHAHRAZAD","DAOULIZ").forEach(nameCinema->{
                Cinema cinema= new Cinema();
                cinema.setName(nameCinema);
                cinema.setNombreSalles(3+(int)(Math.random()*7));
                cinema.setVille(v);
                cinemaRepository.save(cinema);
            });
        });
    }

    @Override
    public void initSalles() {
      cinemaRepository.findAll().forEach(cinema->{
          for(int i=0;i<cinema.getNombreSalles();i++){
              Salle salle = new Salle();
              salle.setName("salle"+(i+1));
              salle.setCinema(cinema);
              salle.setNombrePlaces(15+(int)(Math.random()*10));
              salleRepository.save(salle);
          }
      });
    }

    @Override
    public void initPlaces() {
   salleRepository.findAll().forEach(salle->{
       for (int i=0;i<salle.getNombrePlaces();i++){
           Place place =new Place();
           place.setNumero(i+1);
           place.setSalle(salle);
           placeRepository.save(place);
       }
   });}

    @Override
    public void initSeances() {
        DateFormat dateformat = new SimpleDateFormat("HH:mm");
        Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(s->{
            Seance seance = new Seance();
            try {
                seance.setDateDebut(dateformat.parse(s));
                seanceRepository.save(seance);
            } catch (ParseException e){
                e.printStackTrace();}
        });}

    @Override
    public void initCategories() {
        Stream.of("Histoire","Actions","Fiction","Drama").forEach(a->{
           Categorie categorie = new Categorie();
           categorie.setName(a);
           categoryRepository.save(categorie);
    });
    }

    @Override
    public void initFilms() {
      double[] durees = new double[]{1,1.15,1.3,2,2.3,3};
        List<Categorie> categories =categoryRepository.findAll();
        Stream.of("MALEFIQUE"," NUIT MAGIQUE","C'EST LA VIE","WAKANDA FOREVER","ECANTO","ARTEMIS FOWL").forEach(a->{
            Film film= new Film();
            film.setTitre(a);
            film.setDuree(durees[new Random().nextInt(durees.length)]);
            film.setPhoto(a.replace(" ",""));
            film.setCategorie(categories.get(new Random().nextInt(categories.size())));
            filmRepository.save(film);
        });

    }

    @Override  // @Transactional ca veut dire que toutes les operations que vous faites la , se trouvent au sein de meme Transaction , et quand vous etes dans le meme Transaction du moment que vous utilisez le mode LAZY
    //
    public void initProjetcions() {
        double[] prices = new double[]{50,70,90,150,200};
        villeRepository.findAll().forEach(ville ->{
            ville.getCinemas().forEach(cinema->{
                cinema.getSalles().forEach(salle -> {
                    filmRepository.findAll().forEach(film->{
                        seanceRepository.findAll().forEach(seance->{
                            Projection projection = new Projection();
                            projection.setDateProjection(new Date());
                            projection.setFilm(film);
                            projection.setSalle(salle);
                            projection.setPrix(prices[new Random().nextInt(prices.length)]);
                            projection.setSeance(seance);
                            projectionRepository.save(projection);

                        });
                    });

                });
            });
        });

    }

    @Override
    public void initTickets() {
      projectionRepository.findAll().forEach(pro->{
          pro.getSalle().getPlaces().forEach(place->{
              Ticket ticket =new Ticket();
              ticket.setPlace(place);
              ticket.setProjection(pro);
              ticket.setPrix(pro.getPrix());
              ticket.setReservee(false);
              ticketRepository.save(ticket);
          });
      });
    }
}
