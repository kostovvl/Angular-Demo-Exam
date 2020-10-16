package examapi.contentservice.service;

import examapi.contentservice.domain.dto.CommentDto;
import examapi.contentservice.domain.entity.Comment;
import examapi.contentservice.domain.entity.Post;
import examapi.contentservice.repository.CommentRepository;
import examapi.contentservice.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Transactional
    public CommentDto createComment(CommentDto newComment) {
        Post post = this.postRepository.getOne(newComment.getPostId());
        Comment comment = this.mapper.map(newComment, Comment.class);
        comment.setPost(post);
        comment.setApproved(true);

        return this.mapper.map(this.commentRepository.saveAndFlush(comment), CommentDto.class);
    }

    public CommentDto updateComment(long id, CommentDto updatedComment) {
        Comment comment = this.commentRepository.getOne(id);
        comment.setContent(updatedComment.getContent());

        this.commentRepository.saveAndFlush(comment);

        return updatedComment;
    }

    public List<CommentDto> getByPost(long postId) {
        return this.commentRepository.getCommentsForPost(postId)
                .stream()
                .map(c -> this.mapper.map(c, CommentDto.class))
                .collect(Collectors.toList());
    }

    public CommentDto getById(long id) {
        return this.commentRepository.findById(id)
                .map(c -> this.mapper.map(c, CommentDto.class))
                .orElse(null);
    }

    public void approve(long id) {
        Comment comment = this.commentRepository.getOne(id);
        comment.setApproved(true);
        this.commentRepository.saveAndFlush(comment);
    }


    @Transactional
    public void delete(long id) {
        this.commentRepository.deleteById(id);
    }

}
