package examapi.userservice.web;

import examapi.userservice.domain.dto.UserEntityDto;
import examapi.userservice.domain.entity.AllUsers;
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

    @GetMapping("/repository")
    public ResponseEntity<?> repositoryEmpty() {
        return new ResponseEntity<>(this.userEntityService.repositoryEmpty(), HttpStatus.OK);
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

    @GetMapping("/all/{apiKey}")
    public ResponseEntity<?>getAllExceptRootAdmin(@PathVariable(name = "apiKey") String apiKey) {
        checkKey(apiKey);
        AllUsers result = new AllUsers(this.userEntityService.findAllWithoutRootAdmin());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update/{id}/{apiKey}")
    public void updateToAdmin(@PathVariable(name = "id") long id,
                              @PathVariable(name = "apiKey") String apiKey) {

        checkKey(apiKey);
        this.userEntityService.updateToAdmin(id);
    }

    @PutMapping("/downgrade/{id}/{apiKey}")
    public void downgradeToUser(@PathVariable(name = "id") long id,
                              @PathVariable(name = "apiKey") String apiKey) {
        checkKey(apiKey);
        this.userEntityService.downgradeToUser(id);
    }

    private void checkKey(String key) {
        if (!this.apiKey.getKey().equals(key)) {
            throw new IllegalArgumentException("Unauthorized");
        }
    }

}
