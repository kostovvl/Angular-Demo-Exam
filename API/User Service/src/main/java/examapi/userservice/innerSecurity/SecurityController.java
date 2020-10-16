package examapi.userservice.innerSecurity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    private final ApiKey apiKey;

    public SecurityController(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    @PostMapping("/security")
    public void setKey(@RequestBody String key) {
        this.apiKey.setKey(key);
    }

}
