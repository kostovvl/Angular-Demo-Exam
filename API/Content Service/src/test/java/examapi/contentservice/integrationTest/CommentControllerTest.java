package examapi.contentservice.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import examapi.contentservice.domain.dto.AllCommentsDto;
import examapi.contentservice.domain.dto.CommentDto;
import examapi.contentservice.domain.entity.Comment;
import examapi.contentservice.domain.entity.Post;
import examapi.contentservice.innerSecurity.ApiKey;
import examapi.contentservice.repository.CommentRepository;
import examapi.contentservice.repository.PostRepository;
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

import javax.transaction.Transactional;
import java.util.HashSet;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ApiKey apiKey;

    @Autowired
    private ObjectMapper objectMapper;

    private final String apiPass = "123456";

    private Post post1;
    private Post post2;

    private CommentDto comment1;
    private CommentDto comment2;


    @BeforeEach
    public void setUp_Posts() {
        this.commentRepository.deleteAll();
        this.postRepository.deleteAll();

        this.apiKey.setKey(apiPass);

        this.post1 = new Post();
        this.post1.setTitle("Post 1");
        this.post1.setComments(new HashSet<>());


        this.post2 = new Post();
        this.post2.setTitle("Post 2");

       this.postRepository.saveAndFlush(this.post1);
       this.postRepository.saveAndFlush(this.post2);
       this.post1.setComments(new HashSet<>());

        long post1Id = this.postRepository.findByTitle(this.post1.getTitle()).orElse(null).getId();
        long post2Id = this.postRepository.findByTitle(this.post2.getTitle()).orElse(null).getId();

        this.comment1 = new CommentDto();
        this.comment1.setId(1);
        this.comment1.setContent("Content 1");
        this.comment1.setApproved(true);
        this.comment1.setPostId(post1Id);

        this.comment2 = new CommentDto();
        this.comment2.setId(2);
        this.comment2.setContent("Content 2");
        this.comment2.setApproved(true);
        this.comment2.setPostId(post2Id);
    }

    @AfterEach
    public void setDown() {
        this.commentRepository.deleteAll();
        this.postRepository.deleteAll();
    }

    @Transactional
    @Test
    public void should_Create_Comment() throws Exception {
        //act
        System.out.println();
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/comments/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.comment1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        Assertions.assertEquals(1, this.commentRepository.count());

    }

    @Test
    public void should_Update_Comment() throws Exception {
       //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/comments/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.comment1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        Comment seeded = this.commentRepository.findByContent(this.comment1.getContent()).orElse(null);

        //act
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/comments/update/" + seeded.getId() + "/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.comment2))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        Comment result = this.commentRepository.findById(seeded.getId()).orElse(null);

        //assert
        Assertions.assertEquals(this.comment2.getContent(), result.getContent());


    }

    @Test
    public void should_Get_For_Post() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/comments/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.comment1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/comments/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.comment2))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        long postId = this.postRepository.findByTitle(this.post1.getTitle()).orElse(null).getId();

        //act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/comments/getForPost/" + postId + "/" + apiPass)
        ).andReturn();
        AllCommentsDto result1 = this.objectMapper.readValue(result.getResponse().getContentAsString(),
                AllCommentsDto.class);
        CommentDto recieved = result1.getAll().get(0);

        //assert
        Assertions.assertEquals(this.comment1.getContent(), recieved.getContent());


    }

    @Test
    public void should_Get_By_Id() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/comments/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.comment1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        Comment seeded = this.commentRepository.findByContent(this.comment1.getContent()).orElse(null);

        //act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/comments/getById/" + seeded.getId() + "/" + apiPass)
        ).andReturn();
        CommentDto result1 = this.objectMapper.readValue(result.getResponse().getContentAsString(),
                CommentDto.class);

        //assert
        Assertions.assertEquals(this.comment1.getContent(), result1.getContent());


    }

    //They Are automatically approved because I shutdown this feature
    @Test
    public void should_Approve_Comment() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/comments/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.comment1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        Comment seeded = this.commentRepository.findByContent(this.comment1.getContent()).orElse(null);

        //act
        mockMvc.perform(MockMvcRequestBuilders
                .put("/comments/approve/" + seeded.getId() + "/" + apiPass)
        ).andExpect(status().isOk());
        seeded = this.commentRepository.findByContent(this.comment1.getContent()).orElse(null);

        //assert
        Assertions.assertTrue(seeded.isApproved());


    }

    @Test
    public void should_Delete_Comments() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/comments/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.comment1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        Comment seeded = this.commentRepository.findByContent(this.comment1.getContent()).orElse(null);

        //act
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/comments/delete/" + seeded.getId() + "/" + apiPass)
        ).andExpect(status().isOk());

        this.postRepository.deleteAll();
        this.commentRepository.deleteAll();

        //assert
        Assertions.assertEquals(0, this.commentRepository.count());
    }

}
