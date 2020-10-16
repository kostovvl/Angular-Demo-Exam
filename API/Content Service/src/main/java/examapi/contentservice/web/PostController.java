package examapi.contentservice.web;

import examapi.contentservice.domain.dto.PostDto;
import examapi.contentservice.domain.dto.AllPostsDto;
import examapi.contentservice.innerSecurity.ApiKey;
import examapi.contentservice.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final ApiKey apiKey;

    public PostController(PostService postService, ApiKey apiKey) {
        this.postService = postService;
        this.apiKey = apiKey;
    }

    @PostMapping("/create/{apiKey}")
    public ResponseEntity<?> create (@PathVariable(name = "apiKey") String apiKey,
                                     @RequestBody PostDto postDto) {
        this.apiKey.checkKey(apiKey);
        PostDto result = this.postService.create(postDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update/{postId}/{apiKey}")
    public ResponseEntity<?> update(@PathVariable(name = "postId") long postId,
                                    @PathVariable(name = "apiKey") String apiKey,
                                    @RequestBody() PostDto updatedPost) {
        this.apiKey.checkKey(apiKey);
        PostDto result = this.postService.update(postId, updatedPost);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/all/{apiKey}")
    public ResponseEntity<?> all(@PathVariable(name = "apiKey") String apiKey) {
        this.apiKey.checkKey(apiKey);
        AllPostsDto allPosts = new AllPostsDto(this.postService.getAll());
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @GetMapping("/byCategory/{categoryId}/{apiKey}")
    public ResponseEntity<?> all(@PathVariable(name = "categoryId") long categoryId,
                                 @PathVariable(name = "apiKey") String apiKey) {
        this.apiKey.checkKey(apiKey);
        AllPostsDto allPosts = new AllPostsDto(this.postService.getForCategory(categoryId));
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @GetMapping("/details/{postId}/{apiKey}")
    public ResponseEntity<?> getById(@PathVariable(name = "postId") long postId,
                                     @PathVariable(name = "apiKey") String apiKey) {
        this.apiKey.checkKey(apiKey);
        PostDto result = this.postService.getById(postId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/approve/{postId}/{apiKey}")
    public ResponseEntity<?> approve(@PathVariable(name = "postId") long postId,
                                     @PathVariable(name = "apiKey") String apiKey) {
        this.apiKey.checkKey(apiKey);
        this.postService.approve(postId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}/{apiKey}")
    public ResponseEntity<?> delete(@PathVariable(name = "postId") long postId,
                                     @PathVariable(name = "apiKey") String apiKey) {
        this.apiKey.checkKey(apiKey);
        this.postService.delete(postId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
