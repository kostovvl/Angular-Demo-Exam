package examapi.adminservice.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import examapi.adminservice.domain.AllComments;
import examapi.adminservice.domain.Comment;
import examapi.adminservice.innerSecurity.ApiKey;
import examapi.adminservice.repository.CommentRepository;
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
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApiKey apiKey;

    private final String apiPass = "123456";

    @BeforeEach
    public void setUp() {
        this.commentRepository.deleteAll();
        this.apiKey.setKey(apiPass);
    }

    @Test()
    public void should_Add_New_Comment() throws Exception {
        //act
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/comments/add/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\" : 1, \"creatorName\" : \"Vlado\"}")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        //assert
        Assertions.assertEquals(this.commentRepository.count() ,1);
    }

    @Test()
    public void should_Return_All_Comments() throws Exception {
        //arrange
        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setCreatorName("Creator1");
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/comments/add/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(comment1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Comment comment2 = new Comment();
        comment1.setId(2);
        comment1.setCreatorName("Creator2");
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/comments/add/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(comment2))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        MvcResult result =  this.mockMvc.perform(MockMvcRequestBuilders
                .get("/comments/all/" + apiPass)
        ).andExpect(status().isOk()).andReturn();

        //act
        AllComments result1 = this.objectMapper.readValue(result.getResponse().getContentAsString(),
                AllComments.class);

        //assert
        Assertions.assertEquals(2, result1.getAll().size());
    }

    @Test
    public void should_Delete_Comment_When_Approved() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/comments/add/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\" : 1, \"creatorName\" : \"Vlado\"}")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        //

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/comments/delete/1/"  + apiPass)
        );

        //assert
        Assertions.assertEquals(0, this.commentRepository.count());
    }

}
