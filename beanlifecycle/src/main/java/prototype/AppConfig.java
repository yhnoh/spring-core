package prototype;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class AppConfig {

    @Bean
    @Scope("prototype")
    public Client client(){
        Client client = new Client();
        client.setHost("111.111.111.111");
        return client;
    }
}
