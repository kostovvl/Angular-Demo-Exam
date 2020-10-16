package examapi.contentservice.integrationTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import examapi.contentservice.domain.dto.AllCategories;
import examapi.contentservice.domain.dto.CategoryDto;
import examapi.contentservice.domain.entity.Category;
import examapi.contentservice.innerSecurity.ApiKey;
import examapi.contentservice.repository.CategoryRepository;
import examapi.contentservice.service.CategoryService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ApiKey apiKey;

    @Autowired
    private ObjectMapper objectMapper;

    private final String apiPass = "123456";
    private CategoryDto category1;
    private CategoryDto category2;

    @BeforeEach
    public void setUp() {
        this.categoryRepository.deleteAll();
        this.apiKey.setKey(apiPass);

        this.category1 = new CategoryDto();
        this.category1.setName("Category 1");

        this.category2 = new CategoryDto();
        this.category2.setName("Category 2");
    }

    @AfterEach
    public void setDown(){
        this.categoryRepository.deleteAll();
    }

    @Test
    public void should_Add_Entity() throws Exception {
        //act
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/categories/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.category1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        CategoryDto result1 = this.objectMapper.readValue(result.getResponse().getContentAsString(),
                CategoryDto.class);

        //assert
        Assertions.assertEquals(this.category1.getId(), result1.getId());
        Assertions.assertEquals(this.category1.getName(), result1.getName());
    }

    @Test
    public void should_Return_All() throws Exception {
        //arrange
         this.mockMvc.perform(MockMvcRequestBuilders
                .post("/categories/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.category1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/categories/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.category2))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        //act
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/categories/all/" + apiPass)
        ).andExpect(status().isOk()).andReturn();
        AllCategories result1 = this.objectMapper.readValue(result.getResponse().getContentAsString(),
                AllCategories.class);

        //assert
        Assertions.assertEquals(2, result1.getAll().size());
    }

    @Test
    public void should_Update_Category() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/categories/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.category1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        long id = this.categoryRepository.findByName(this.category1.getName()).orElse(null).getId();

        //act
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/categories/update/" + id + "/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.category2))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        Category result = this.categoryRepository.findById(id).orElse(null);

        //assert
        Assertions.assertEquals(this.category2.getName(), result.getName());
    }

    @Test
    public void should_Delete() throws Exception {
        //arrange
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/categories/create/" + apiPass)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.category1))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        long id = this.categoryRepository.findByName(this.category1.getName()).orElse(null).getId();

        //act
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/categories/delete/" + id + "/" + apiPass)
        ).andExpect(status().isOk());

        //assert
        Assertions.assertEquals(0, this.categoryRepository.count());
    }

}
