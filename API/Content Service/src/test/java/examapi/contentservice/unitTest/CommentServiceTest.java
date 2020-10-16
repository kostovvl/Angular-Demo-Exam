package examapi.contentservice.unitTest;

import examapi.contentservice.domain.dto.CommentDto;
import examapi.contentservice.domain.entity.Comment;
import examapi.contentservice.repository.CommentRepository;
import examapi.contentservice.repository.PostRepository;
import examapi.contentservice.service.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    private Comment comment1;
    private Comment comment2;

    @BeforeEach
    public void setUp() {
        this.commentService = new CommentService(commentRepository,
                postRepository, new ModelMapper());

        this.comment1 = new Comment();
        this.comment1.setId(1);
        this.comment1.setContent("Content 1");

        this.comment2 = new Comment();
        this.comment2.setId(2);
        this.comment2.setContent("Content 2");
    }

    @Test
    public void should_Return_All() {
        //arrange
        List<Comment> repoComments = new ArrayList<>();
        repoComments.add(this.comment1);
        repoComments.add(this.comment2);
        when(this.commentRepository.getCommentsForPost(1)).thenReturn(repoComments);

        //act
        List<CommentDto> result = this.commentService.getByPost(1);

        //assert
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void should_Return_By_Id() {
        //assert
        when(this.commentRepository.findById((long)1)).thenReturn(Optional.of(comment1));

        //act
        CommentDto result = this.commentService.getById(1);

        //act
        Assertions.assertEquals(this.comment1.getContent(), result.getContent());
    }

}
