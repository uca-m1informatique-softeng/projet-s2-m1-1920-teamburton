package puertoricotr;

import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * Classe permettant de lancer le jeu.
 */
@SpringBootApplication
public class Jeux {

    public static void main(String[] args) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/json");

        Partie partie = new Partie(2, 2);
        MoteurDuJeux moteurDuJeux = new MoteurDuJeux(partie);

        moteurDuJeux.tourDeJeuSansAffichage();

        Scores scores = new Scores();
        ListeScoreDTO listeScoreDTO = scores.genererStats(partie);
        partie.resetPartie();

        RestTemplate restTemplate = new RestTemplate();
        JSONObject resultats = new JSONObject ();
        String uri = "localhost:8080/score/v1/statistiques/1";
        restTemplate.getForObject(uri, JSONObject.class, resultats);

        System.out.println(resultats.toString());
        SpringApplication.run(Jeux.class, args);
    }
}
