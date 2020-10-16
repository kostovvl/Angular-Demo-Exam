package examapi.contentservice.service;

import examapi.contentservice.domain.dto.CategoryDto;
import examapi.contentservice.domain.entity.Category;
import examapi.contentservice.repository.CategoryRepository;
import examapi.contentservice.repository.CommentRepository;
import examapi.contentservice.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper mapper;

    public CategoryService(CategoryRepository categoryRepository, PostRepository postRepository,
                           CommentRepository commentRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    public CategoryDto addCategory(CategoryDto newCategory) {
        Category category = this.mapper.map(newCategory, Category.class);
        category.setCreatedOn(LocalDateTime.now());
        this.categoryRepository.saveAndFlush(category);

        return newCategory;
    }

    public List<CategoryDto> getAll() {
        return this.categoryRepository.findAll()
                .stream()
                .map(c -> {
                    CategoryDto result = this.mapper.map(c, CategoryDto.class);
                     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                     result.setCreatedOn(c.getCreatedOn().format(formatter));
                    System.out.println();
                     return result;
                })
                .collect(Collectors.toList());
    }

    public void updateCategory(long categoryId, CategoryDto updatedCategory) {
        Category category = this.categoryRepository.getOne(categoryId);

        category.setName(updatedCategory.getName());
        this.categoryRepository.saveAndFlush(category);

    }

    @Transactional
    public void deleteCategory(long categoryId) {

        Category category = this.categoryRepository.getOne(categoryId);

        category.getPosts()
                .stream()
                .forEach(p -> {
                    p.getComments()
                            .forEach(c -> this.commentRepository.deleteById(c.getId()));
                    this.postRepository.deleteById(p.getId());
                });

        this.categoryRepository.deleteById(categoryId);

    }
}
