package puertoricotr;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


@SpringBootTest(classes = WsServeurApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration
public class SpringIntegrationTest {

    public ArrayList<String> arrayBody = new ArrayList<String>();
    public HttpStatus statusCode;


    public void executeGet(String url) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        statusCode = response.getStatusCode();
    }

    public void executeGetArray(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList> response = restTemplate.getForEntity(url, ArrayList.class);
        statusCode = response.getStatusCode();
    }

    void executePost(String url, String vainqueur) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, vainqueur,String.class);
        statusCode = response.getStatusCode();

    }

    void addWinners(String botName){
        this.arrayBody.add(botName);
    }

}