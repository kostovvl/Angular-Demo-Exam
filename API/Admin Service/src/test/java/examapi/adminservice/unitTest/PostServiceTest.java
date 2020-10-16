package examapi.adminservice.unitTest;

import examapi.adminservice.domain.Post;
import examapi.adminservice.repository.PostsRepository;
import examapi.adminservice.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PostServiceTest {


    private PostService postService;
    private Post post1;
    private Post post2;

    @Mock
    PostsRepository postsRepository;

    @BeforeEach()
    public void setUp() {
        //initializeService
        this.postsRepository.deleteAll();
        this.postService = new PostService(this.postsRepository);

        //initialize entities
        this.post1 = new Post();
        this.post1.setId(1);
        this.post1.setTitle("Test Post 1");

        this.post1 = new Post();
        this.post1.setId(2);
        this.post1.setTitle("Test Post 2");
    }

    @Test
    public void should_Return_All_Posts() {
        //arrange
        List<Post> repoPosts = new ArrayList<>();
        repoPosts.add(this.post1);
        repoPosts.add(this.post2);
        when(this.postsRepository.findAll()).thenReturn(repoPosts);

        //act
        List<Post> result = this.postService.getAll();

        //assert
        Assertions.assertEquals(result.size(), 2);
    }

}
