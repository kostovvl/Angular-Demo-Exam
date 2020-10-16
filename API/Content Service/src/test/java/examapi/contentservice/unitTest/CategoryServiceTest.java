package examapi.contentservice.unitTest;

import examapi.contentservice.domain.dto.CategoryDto;
import examapi.contentservice.domain.entity.Category;
import examapi.contentservice.repository.CategoryRepository;
import examapi.contentservice.repository.CommentRepository;
import examapi.contentservice.repository.PostRepository;
import examapi.contentservice.service.CategoryService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    public void setUp() {
        this.categoryService = new CategoryService(this.categoryRepository,
                this.postRepository, this.commentRepository, new ModelMapper());
    }

    @Test
    public void should_Return_All_Categories() {
        //assert
        Category category1 = new Category();
        category1.setId(1);
        category1.setCreatedOn(LocalDateTime.now());
        category1.setName("Category1");

        Category category2 = new Category();
        category2.setId(2);
        category2.setCreatedOn(LocalDateTime.now());
        category2.setName("Category2");

        List<Category> repoCategories = new ArrayList<>();
        repoCategories.add(category1);
        repoCategories.add(category2);
        when(this.categoryRepository.findAll()).thenReturn(repoCategories);

        //act
        List<CategoryDto> result = this.categoryService.getAll();

        //assert
        Assertions.assertEquals(2, result.size());
    }


}
