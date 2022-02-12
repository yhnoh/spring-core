package inter;

import org.springframework.context.annotation.Bean;

public class AppConfig {

    @Bean
    public Client client(){
        Client client = new Client();
        client.setHost("111.111.111.111");
        return client;
    }
}
