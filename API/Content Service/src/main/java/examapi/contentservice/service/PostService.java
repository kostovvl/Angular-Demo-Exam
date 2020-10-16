package examapi.contentservice.service;

import examapi.contentservice.domain.dto.PostDto;
import examapi.contentservice.domain.entity.Category;
import examapi.contentservice.domain.entity.Post;
import examapi.contentservice.repository.CategoryRepository;
import examapi.contentservice.repository.CommentRepository;
import examapi.contentservice.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper mapper;

    public PostService(PostRepository postRepository, CategoryRepository categoryRepository,
                       CommentRepository commentRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Transactional
    public PostDto create(PostDto newPost) {

        Post post = this.mapper.map(newPost, Post.class);
        Category category = this.categoryRepository.getOne(newPost.getCategoryId());

        post.setApproved(false);
        post.setCategory(category);

        return this.mapper.map(this.postRepository.saveAndFlush(post), PostDto.class);
    }

    public PostDto update(long postId, PostDto updated) {
        Post post = this.postRepository.getOne(postId);

        post.setContent(updated.getContent());
        post.setTitle(updated.getTitle());

        this.postRepository.saveAndFlush(post);

        return updated;
    }

    public List<PostDto> getAll() {
        return this.postRepository.findAllApproved()
                .stream()
                .map(p -> {
                    PostDto result = this.mapper.map(p, PostDto.class);
                    result.setCategoryId(p.getCategory().getId());
                    return result;
                }).collect(Collectors.toList());
    }

    public List<PostDto> getForCategory(long categoryId) {
        return this.postRepository.findAllApprovedByCategory(categoryId)
                .stream()
                .map(p -> {
                    PostDto result = this.mapper.map(p, PostDto.class);
                    result.setCategoryId(p.getCategory().getId());
                    return result;
                }).collect(Collectors.toList());
    }

    public PostDto getById(long postId) {
        return this.postRepository.findById(postId)
                .map(p -> {
                    PostDto result = this.mapper.map(p, PostDto.class);
                    result.setCategoryId(p.getCategory().getId());
                    return result;
                }).orElse(null);
    }

    public void approve(long postId) {
        Post post = this.postRepository.getOne(postId);
        post.setApproved(true);
        this.postRepository.saveAndFlush(post);
    }

    @Transactional
    public void delete(long postId) {
        Post post = this.postRepository.getOne(postId);
        post.getComments().forEach(c -> this.commentRepository.deleteById(c.getId()));

        this.postRepository.deleteById(postId);
    }

}
