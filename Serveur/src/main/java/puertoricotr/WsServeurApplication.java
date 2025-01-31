package puertoricotr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WsServeurApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsServeurApplication.class, args);
        System.out.print("-----------------------------------------------------------------------------" + '\n' + '\t' +
                "Application WsServeurApplication is running! access URL:" + '\n' + '\t' +
                "Local:" + '\t' + '\t' + "http://localhost:8080/" + '\n' + "-----------------------------------------------------------------------------" + '\n');
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
