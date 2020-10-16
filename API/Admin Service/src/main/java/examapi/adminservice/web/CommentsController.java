package examapi.adminservice.web;

import examapi.adminservice.domain.AllComments;
import examapi.adminservice.domain.Comment;
import examapi.adminservice.innerSecurity.ApiKey;
import examapi.adminservice.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    private final ApiKey apiKey;
    private final CommentService commentService;
    private final ContentClient contentClient;

    public CommentsController(ApiKey apiKey, CommentService commentService, ContentClient contentClient) {
        this.apiKey = apiKey;
        this.commentService = commentService;
        this.contentClient = contentClient;
    }

    @PostMapping("/add/{apiKey}")
    public void add(@PathVariable(name = "apiKey") String apiKey,
                    @RequestBody Comment comment) {
        this.apiKey.checkKey(apiKey);
        this.commentService.add(comment);
    }

    @GetMapping("/all/{apiKey}")
    public ResponseEntity<?> getAll(@PathVariable(name = "apiKey") String apiKey) {
        this.apiKey.checkKey(apiKey);
        AllComments result = new AllComments(this.commentService.getAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/approve/{commentId}/{apiKey}")
    public void approve(@PathVariable(name = "commentId") long commentId,
                        @PathVariable(name = "apiKey") String apiKey) {
        this.apiKey.checkKey(apiKey);
        this.contentClient.approveComment(commentId);
        this.commentService.delete(commentId);
    }

    @DeleteMapping("/delete/{commentId}/{apiKey}")
    public void delete(@PathVariable(name = "commentId") long commentId,
                        @PathVariable(name = "apiKey") String apiKey) {
        this.apiKey.checkKey(apiKey);
        this.contentClient.deleteComment(commentId);
        this.commentService.delete(commentId);
    }


}
