package examapi.adminservice.unitTest;

import examapi.adminservice.domain.Comment;
import examapi.adminservice.repository.CommentRepository;
import examapi.adminservice.service.CommentService;
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
public class CommentServiceTest {

    private CommentService commentService;
    private Comment comment1;
    private Comment comment2;

    @Mock
    CommentRepository commentRepository;

    @BeforeEach()
    public void setUp() {
        this.commentRepository.deleteAll();

        //initialize service
        this.commentService = new CommentService(this.commentRepository);

        //initialize entities
        this.comment1 = new Comment();
        this.comment1.setId(1);
        this.comment1.setCreatorName("Creator 1");

        this.comment2 = new Comment();
        this.comment2.setId(2);
        this.comment2.setCreatorName("Creator 2");
    }

    @Test
    public void should_Return_All_Comments() {
        //arrange
        List<Comment> repoComments = new ArrayList<>();
        repoComments.add(this.comment1);
        repoComments.add(this.comment2);
        when(this.commentRepository.findAll()).thenReturn(repoComments);

        //act
        List<Comment> result = this.commentService.getAll();

        //assert
        Assertions.assertEquals(result.size(), 2);
    }

}
