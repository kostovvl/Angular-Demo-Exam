package examapi.userservice.web;

import examapi.userservice.domain.dto.UserEntityDto;
import examapi.userservice.innerSecurity.ApiKey;
import examapi.userservice.service.UserEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class UserController {

    private final UserEntityService userEntityService;
    private final ApiKey apiKey;

    public UserController(UserEntityService userEntityService, ApiKey apiKey) {
        this.userEntityService = userEntityService;
        this.apiKey = apiKey;
    }

    @PostMapping("/register/{apiKey}")
    public ResponseEntity<?> registerUser(@PathVariable(name = "apiKey") String key,
                                          @RequestBody UserEntityDto newUser) {

            checkKey(key);
            UserEntityDto result = this.userEntityService.registerUser(newUser);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/login/{username}/{apiKey}")
    public ResponseEntity<?> loginUser(@PathVariable(name = "username") String username,
                                    @PathVariable(name = "apiKey") String key) {
        try {
            checkKey(key);
            UserEntityDto result = this.userEntityService.findByUsername(username);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedOperationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private void checkKey(String key) {
        if (!this.apiKey.getKey().equals(key)) {
            throw new IllegalArgumentException("Unauthorized");
        }
    }

}
