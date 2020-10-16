package examapi.contentservice.unitTest;

import examapi.contentservice.domain.dto.CategoryDto;
import examapi.contentservice.domain.dto.PostDto;
import examapi.contentservice.domain.entity.Category;
import examapi.contentservice.domain.entity.Post;
import examapi.contentservice.repository.CategoryRepository;
import examapi.contentservice.repository.CommentRepository;
import examapi.contentservice.repository.PostRepository;
import examapi.contentservice.service.CategoryService;
import examapi.contentservice.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    private Category category1;
    private Category category2;
    private Post post1;
    private Post post2;

    @BeforeEach()
    public void setUp() {
        this.postService = new PostService(this.postRepository,
                this.categoryRepository, this.commentRepository, new ModelMapper());

        this.category1 = new Category();
        this.category1.setId(1);

        this.category2 = new Category();
        this.category2.setId(2);

        this.post1 = new Post();
        this.post1.setId(1);
        this.post1.setTitle("Title 1");
        this.post1.setContent("Content 1");
        this.post1.setCategory(category1);


        this.post2 = new Post();
        this.post2.setId(1);
        this.post2.setTitle("Title 2");
        this.post2.setContent("Content 2");
        this.post2.setCategory(category2);

    }

    @Test
    public void should_Return_All_Posts() {
        //assert
        List<Post> repoPosts = new ArrayList<>();
        repoPosts.add(this.post1);
        repoPosts.add(this.post2);

        Mockito.lenient().when(this.postRepository.findAllApproved()).thenReturn(repoPosts);

        //act
        List<PostDto> result = this.postService.getAll();

        //assert
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void should_Return_By_CategoryId() {
        //assert
        List<Post> repoPosts = new ArrayList<>();
        repoPosts.add(this.post1);

        when(this.postRepository.findAllApprovedByCategory(1)).thenReturn(repoPosts);

        //act
        List<PostDto> result = this.postService.getForCategory(1);

        //assert
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void should_Return_By_Id() {
        //assert
        when(this.postRepository.findById((long)1)).thenReturn(Optional.of(this.post1));

        //act
        PostDto result = this.postService.getById(1);

        //assert
        Assertions.assertEquals(this.post1.getTitle(), result.getTitle());
    }


}
