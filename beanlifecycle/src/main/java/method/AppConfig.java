package method;

import org.springframework.context.annotation.Bean;

public class AppConfig {

    @Bean(initMethod = "connect", destroyMethod = "close")
    public Client client(){
        Client client = new Client();
        client.setHost("111.111.111.111");
        return client;
    }
}
