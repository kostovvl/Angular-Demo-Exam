package examapi.contentservice.integrationTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import examapi.contentservice.domain.dto.AllPostsDto;
import examapi.contentservice.domain.dto.PostDto;
import examapi.contentservice.domain.entity.Category;
import examapi.contentservice.innerSecurity.ApiKey;
import examapi.contentservice.repository.CategoryRepository;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApiKey apiKey;

    private final String apiPass = "123456";

    private Category category1;
    private Category category2;

    private PostDto post1;
    private PostDto post2;

    @BeforeEach()
    public void setUp(){
        this.postRepository.deleteAll();
        this.categoryRepository.deleteAll();

        this.apiKey.setKey(apiPass);

        this.category1 = new Category();
        this.category2 = new Category();

        this.category1.setName("Category 1");
        this.category2.setName("Category 2");

        this.categoryRepository.saveAll(List.of(category1, category2));

        this.post1 = new PostDto();
        this.post1.setTitle("Post 1");
        this.post1.setCategoryId(this.categoryRepository.findByName(this.category1.getName()).orElse(null).getId());

        this.post2 = new PostDto();
        this.post2.setTitle("Post 2");
        this.post2.setCategoryId(this.categoryRepository.findByName(this.category2.getName()).orElse(null).getId());
    }

    @AfterEach
    public void setDown() {
        this.postRepository.deleteAll();
        this.categoryRepository.deleteAll();
    }

    @Test
    public void should_Add_Post() throws Exception {
        //act
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/posts/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.post1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        //assert
        Assertions.assertEquals(1, this.postRepository.count());
    }

    @Test
    public void should_Update_Post() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/posts/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.post1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        long id = this.postRepository.findByTitle(this.post1.getTitle()).orElse(null).getId();

        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/posts/update/"+ id + "/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.post2))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        //act
        long id2 = this.postRepository.findByTitle(this.post2.getTitle()).orElse(null).getId();
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/posts/details/" + id2 + "/" + apiPass)
        ).andReturn();
        PostDto updated = this.objectMapper.readValue(result.getResponse().getContentAsString(),
                PostDto.class);

        //assert
        Assertions.assertEquals(this.post2.getTitle(), updated.getTitle());
    }

    @Test
    public void should_Return_All_Posts() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/posts/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.post1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/posts/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.post2))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        long id1 = this.postRepository.findByTitle(this.post1.getTitle()).orElse(null).getId();
        this.approve_Post(id1);
        long id2 = this.postRepository.findByTitle(this.post2.getTitle()).orElse(null).getId();
        this.approve_Post(id2);

        //act
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/posts/all/" + apiPass)
        ).andReturn();
        AllPostsDto expectedAll = this.objectMapper.readValue(result.getResponse().getContentAsString(),
                AllPostsDto.class);
        PostDto expectedPost1 = expectedAll.getAll().get(0);
        PostDto expectedPost2 = expectedAll.getAll().get(1);

        //assert
        Assertions.assertEquals(2, expectedAll.getAll().size());
        Assertions.assertEquals(this.post1.getTitle(), expectedPost1.getTitle());
        Assertions.assertEquals(this.post2.getTitle(), expectedPost2.getTitle());
    }

    @Test
    public void should_Return_By_Category_Id() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/posts/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.post1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        long postId = this.postRepository.findByTitle(this.post1.getTitle()).orElse(null).getId();
        long categoryId = this.categoryRepository.findByName(
                this.postRepository.findByTitle(this.post1.getTitle()).orElse(null)
                .getCategory().getName()
        ).orElse(null).getId();

        this.approve_Post(postId);

        //act
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/posts/byCategory/" + categoryId + "/" + apiPass)
        ).andReturn();

        AllPostsDto expectedAll = this.objectMapper.readValue(result.getResponse().getContentAsString(),
                AllPostsDto.class);
        PostDto expectedPost = expectedAll.getAll().get(0);

        //assert
        Assertions.assertEquals(this.post1.getTitle(), expectedPost.getTitle());
    }

    @Test
    public void get_Post_Details_By_Id() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/posts/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.post1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        long postId = this.postRepository.findByTitle(this.post1.getTitle()).orElse(null).getId();
        this.approve_Post(postId);

        //act
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/posts/details/" + postId + "/" + apiPass)
        ).andReturn();

        PostDto expectedPost = this.objectMapper.readValue(result.getResponse().getContentAsString(),
                PostDto.class);

        //assert
        Assertions.assertEquals(this.post1.getTitle(), expectedPost.getTitle());
    }

    @Test
    public void should_Approve_Post() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/posts/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.post1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        long postId = this.postRepository.findByTitle(this.post1.getTitle()).orElse(null).getId();

        //act
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/posts/approve/"+ postId + "/" + apiPass)
        );
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/posts/details/" + postId + "/" + apiPass)
        ).andReturn();
        PostDto expectedPost = this.objectMapper.readValue(result.getResponse().getContentAsString(),
                PostDto.class);

        //assert
        Assertions.assertTrue(expectedPost.isApproved());

    }

    @Test
    public void should_Delete_Post() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/posts/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.post1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        long postId = this.postRepository.findByTitle(this.post1.getTitle()).orElse(null).getId();

        //act
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/posts/delete/"+ postId + "/" + apiPass)
        );

        //assert
        Assertions.assertEquals(0, this.postRepository.count());
    }



    private void approve_Post(long postId) throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/posts/approve/"+ postId + "/" + apiPass)
        );
    }
}
