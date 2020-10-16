package examapi.userservice.integrationTest;

import examapi.userservice.domain.dto.UserEntityDto;
import examapi.userservice.innerSecurity.ApiKey;
import examapi.userservice.repository.UserEntityRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    public ApiKey apiKey;

    private static final String apiPass = "123456";

    @BeforeEach
    public void setUp() {
        this.userEntityRepository.deleteAll();

        UserEntityDto newUser = new UserEntityDto();
        newUser.setUsername("Vlado");
        newUser.setPassword("123456");
        this.apiKey.setKey(apiPass);

    }

    @Test
    public void shouldRegisterUser() throws Exception {
        //act
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"Vlado\", \"password\": \"123456\" }")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Assertions.assertEquals(this.userEntityRepository.count(), 1);
    }

    @Test
    public void shouldFindAlreadyRegisteredUser() throws Exception {
        //assert
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"Smail\", \"password\": \"123456\" }")
                .accept(MediaType.APPLICATION_JSON)
        );

        String username = this.userEntityRepository.findByUsername("Smail").orElse(null).getUsername();
        System.out.println();


        //act/assert
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/login/" + username + "/" + apiPass)
        ).andExpect(status().isOk());
    }

}
