package puertoricotr;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;


/**
 * Classe permettant de lancer le jeu.
 */
@SpringBootApplication
public class Jeux {

    public static void main(String[] args) {


        Partie partie = new Partie(2, 2);
        MoteurDuJeux moteurDuJeux = new MoteurDuJeux(partie);

        String uriPost = "http://localhost:2480/score/v1/statistiques/save";

        for (int i=0; i < 10; i++){
            moteurDuJeux.tourDeJeuSansAffichage();
            String vainqueur = partie.getVainqueur();
            RestTemplate postRestTemplate = new RestTemplate();
            postRestTemplate.postForObject(uriPost, vainqueur, String.class);
            partie.resetPartie();
        }

        for (int i=1; i < 11; i++) {
            RestTemplate getRestTemplate = new RestTemplate();
            String resultats = null;
            String uriGet = "http://localhost:2480/score/v1/statistiques/" + i;
            resultats = getRestTemplate.getForObject(uriGet, String.class);

            System.out.println(resultats);
        }
        SpringApplication.run(Jeux.class, args);
    }
}
