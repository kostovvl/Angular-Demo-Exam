package examapi.gateway.innerSecurity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {

    private final SecurityClient securityClient;

    public Init(SecurityClient securityClient) {
        this.securityClient = securityClient;
    }

    @Override
    public void run(String... args) throws Exception {
        this.securityClient.sendKeys();
    }
}
