package puertoricotr;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;


public class Scores {

    public ListeScoreDTO genererStats(Partie partie){

        ListeScoreDTO listeScoreDTO = new ListeScoreDTO(partie);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/json");
        HttpEntity<String> req = new HttpEntity<>(listeScoreDTO.toString(), httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        String uri = "localhost:8080/score/v1/statistiques/save";

        restTemplate.postForObject(uri, req, JSON.getClass());

        return listeScoreDTO;
    }
}
