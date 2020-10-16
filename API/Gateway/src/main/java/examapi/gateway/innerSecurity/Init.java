package examapi.gateway.innerSecurity;

import examapi.gateway.domain.user.UserEntity;
import examapi.gateway.domain.user.UserEntityRole;
import examapi.gateway.web.client.UserClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Init implements CommandLineRunner {

    private final SecurityClient securityClient;
    private final UserClient userClient;
    private final PasswordEncoder passwordEncoder;

    public Init(SecurityClient securityClient, UserClient userClient, PasswordEncoder passwordEncoder) {
        this.securityClient = securityClient;
        this.userClient = userClient;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        this.securityClient.sendKeys();

        if (!this.userClient.repositoryEmpty()) {
            return;
        }

        UserEntity userAdmin = new UserEntity();
        userAdmin.setUsername("Admin");
        userAdmin.setPassword(this.passwordEncoder.encode("123456"));

        this.userClient.postForNewUser(userAdmin);
    }
}
