package examapi.gateway.web.client;

import examapi.gateway.configuration.Global;
import examapi.gateway.domain.user.UserEntity;
import examapi.gateway.innerSecurity.ApiKey;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {

    private final RestTemplate restTemplate;
    private final ApiKey apiKey;

    public UserClient(RestTemplate restTemplate, ApiKey apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public UserEntity postForNewUser(UserEntity newUser) {
        return this.restTemplate.postForObject(Global.User_Service_Url + "register/" + this.apiKey.getKey(),
                newUser, UserEntity.class);
    }
}
