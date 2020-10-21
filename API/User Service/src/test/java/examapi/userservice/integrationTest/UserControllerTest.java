package examapi.userservice.integrationTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import examapi.userservice.domain.dto.UserEntityDto;
import examapi.userservice.domain.entity.AllUsers;
import examapi.userservice.innerSecurity.ApiKey;
import examapi.userservice.repository.UserEntityRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private ApiKey apiKey;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String apiPass = "123456";

    private UserEntityDto user1;
    private UserEntityDto user2;

    @BeforeEach
    public void setUp() {
        this.userEntityRepository.deleteAll();

        this.user1 = new UserEntityDto();
        this.user1.setUsername("Vlado");
        this.user1.setPassword("123456");

        this.user2 = new UserEntityDto();
        this.user2.setUsername("Ignat");
        this.user2.setPassword("123456");


        this.apiKey.setKey(apiPass);
    }

    @AfterEach()
    public void setDown() {
        this.userEntityRepository.deleteAll();
    }

    @Test
    public void shouldRegisterUser() throws Exception {
        //act
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.user1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Assertions.assertEquals(this.userEntityRepository.count(), 1);
    }

    @Test
    public void shouldFindAlreadyRegisteredUser() throws Exception {
        //arrange
        String username = this.user1.getUsername();

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.user1))
                .accept(MediaType.APPLICATION_JSON)
        );

        //act
        MvcResult result =  this.mockMvc.perform(MockMvcRequestBuilders
                .get("/login/" + username + "/" + apiPass)
        ).andReturn();

        UserEntityDto result1 = this.objectMapper.readValue(result.getResponse().getContentAsString(),
                UserEntityDto.class);


       //assert
        Assertions.assertEquals(username, result1.getUsername());
    }

    @Test
    public void should_Return_All_Users() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.user1))
                .accept(MediaType.APPLICATION_JSON)
        );

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.user2))
                .accept(MediaType.APPLICATION_JSON)
        );

        //act
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/all/" + apiPass)
        ).andReturn();

        AllUsers result1 = this.objectMapper.readValue(result.getResponse().getContentAsString(),
                AllUsers.class);

        //assert
        Assertions.assertEquals(2, result1.getAll().size());
    }

    @Test
    public void should_Upgrade_To_Admin() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.user1))
                .accept(MediaType.APPLICATION_JSON)
        );

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.user2))
                .accept(MediaType.APPLICATION_JSON)
        );


        String username = this.user2.getUsername();
        long id = this.userEntityRepository.findByUsername(username).orElse(null).getId();


       //act
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/update/" + id + "/" + apiPass)
        );


        MvcResult result =  this.mockMvc.perform(MockMvcRequestBuilders
                .get("/login/" + username + "/" + apiPass)
        ).andReturn();

        UserEntityDto result1 = this.objectMapper.readValue(result.getResponse().getContentAsString(),
                UserEntityDto.class);

        //assert
        Assertions.assertEquals(2, result1.getRoles().size());
    }

    @Test
    public void should_Downgrade_To_User() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.user1))
                .accept(MediaType.APPLICATION_JSON)
        );

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/register/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.user2))
                .accept(MediaType.APPLICATION_JSON)
        );

        String username = this.user2.getUsername();
        long id = this.userEntityRepository.findByUsername(username).orElse(null).getId();

        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/update/" + id + "/" + apiPass)
        );


        //act
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/downgrade/" + id + "/" + apiPass)
        );

        MvcResult result =  this.mockMvc.perform(MockMvcRequestBuilders
                .get("/login/" + username + "/" + apiPass)
        ).andReturn();

        UserEntityDto result1 = this.objectMapper.readValue(result.getResponse().getContentAsString(),
                UserEntityDto.class);

        //assert
        Assertions.assertEquals("USER", result1.getRoles().iterator().next().getRole());
    }

}
