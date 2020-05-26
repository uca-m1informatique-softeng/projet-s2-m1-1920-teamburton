package puertoricotr;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;


public class Scores {

    public ListeScoreDTO genererStats(Partie partie){

        ListeScoreDTO listeScoreDTO = new ListeScoreDTO(partie);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<ListeScoreDTO> requete = new HttpEntity<>(listeScoreDTO, httpHeaders);

        RestTemplate getRestTemplate = new RestTemplate();
        String uri = "http://localhost:2480/score/v1/statistiques/save";

        getRestTemplate.postForObject(uri, requete, JSON.getClass());

        return listeScoreDTO;
    }
}
