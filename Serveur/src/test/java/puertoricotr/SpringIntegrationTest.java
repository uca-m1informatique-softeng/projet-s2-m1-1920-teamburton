package puertoricotr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootTest(classes = WsServeurApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration
public class SpringIntegrationTest {

    public ClientHttpResponse theResponse;
    public ArrayList arrayBody = new ArrayList<String>();
    public String body;

    @Autowired
    protected RestTemplate restTemplate;

    public void executeGet(String url) throws IOException {
        /*final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();
        theResponse = restTemplate.execute(url, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.hadError) {
                return (errorHandler.getResults());
            } else {
                return (response);
            }
        });*/
        System.out.println("\n" + url + "\n");
        this.body = restTemplate.getForObject(url, String.class);
    }

    public void executeGetArray(String url) throws IOException {
        /*final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();
        theResponse = restTemplate.execute(url, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.hadError) {
                return (errorHandler.getResults());
            } else {
                return (response);
            }
        });*/
        this.arrayBody = restTemplate.getForObject(url, ArrayList.class);
    }

    void executePost(String url, String vainqueur) throws IOException {
        /*final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();
        theResponse = restTemplate.execute(url, HttpMethod.POST, requestCallback, response -> {
            if (errorHandler.hadError) {
                return (errorHandler.getResults());
            } else {
                return (response);
            }
        });*/
        restTemplate.postForObject(url, vainqueur, String.class);
    }

    void add2Winners(){
        arrayBody = new ArrayList<String>();
        this.arrayBody.add("BotTest1");
        this.arrayBody.add("BotTest2");
    }

    void addFirstOf2Winners(String botName){
        arrayBody = new ArrayList<String>();
        this.arrayBody.add(botName);
        this.arrayBody.add("BotTest2");
    }

    private class ResponseResultErrorHandler {
        private ClientHttpResponse results = null;
        private Boolean hadError = false;

        private ClientHttpResponse getResults() {
            return results;
        }

        public boolean hasError(ClientHttpResponse response) throws IOException {
            hadError = response.getRawStatusCode() >= 400;
            return hadError;
        }

        public void handleError(ClientHttpResponse response) throws IOException {
            results = response;
        }
    }
}