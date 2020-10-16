package examapi.gateway.web.client;

import examapi.gateway.configuration.Global;
import examapi.gateway.domain.comment.commentadmin.CommentAdmin;
import examapi.gateway.domain.comment.commentadmin.CommentAdminContainer;
import examapi.gateway.domain.comment.commentuser.Comment;
import examapi.gateway.domain.post.postadmin.PostAdmin;
import examapi.gateway.domain.post.postadmin.PostAdminContainer;
import examapi.gateway.domain.post.postuser.Post;
import examapi.gateway.domain.user.UserEntity;
import examapi.gateway.domain.user.UserEntityContainer;
import examapi.gateway.innerSecurity.ApiKey;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class AdminClient {

    private final RestTemplate restTemplate;
    private final ApiKey apiKey;

    public AdminClient(RestTemplate restTemplate, ApiKey apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public void addPostForApproval(Post post) {
        PostAdmin toApprove = new PostAdmin();
        toApprove.setId(post.getId());
        toApprove.setTitle(post.getTitle());
      this.restTemplate.postForObject(Global.Admin_Service_Url + "/posts/add/" +
                this.apiKey.getKey(), toApprove, PostAdmin.class);
    }

    public void addCommentForApproval(Comment comment) {
          this.restTemplate.postForObject(Global.Admin_Service_Url + "/comments/add/" +
                this.apiKey.getKey(), comment, Comment.class);
    }

    public List<PostAdmin> allPostsForApproval() {
        return this.restTemplate.getForObject(Global.Admin_Service_Url + "/posts/all/" + this.apiKey.getKey(),
                PostAdminContainer.class).getAll();
    }

    public List<CommentAdmin> allCommentsForApproval() {
        return this.restTemplate.getForObject(Global.Admin_Service_Url + "/comments/all/" + this.apiKey.getKey(),
                CommentAdminContainer.class).getAll();
    }

    public void approvePost(long postId) {
        this.restTemplate.put(Global.Admin_Service_Url + "/posts/approve/"
        + postId + "/" + this.apiKey.getKey(), String.class);
    }

    public void deletePost(long postId) {
        this.restTemplate.delete(Global.Admin_Service_Url + "/posts/delete/"
                + postId + "/" + this.apiKey.getKey());
    }

    public void approveComment(long commentId) {
        this.restTemplate.put(Global.Admin_Service_Url + "/comments/approve/"
                + commentId + "/" + this.apiKey.getKey(), String.class);
    }

    public void deleteComment(long commentId) {
        this.restTemplate.delete(Global.Admin_Service_Url + "/comments/delete/"
        + commentId + "/" + this.apiKey.getKey());
    }

    public List<UserEntity>allUsers() {
        return this.restTemplate.getForObject(Global.User_Service_Url + "/all/" + this.apiKey.getKey(),
                UserEntityContainer.class).getAll();
    }

    public void updateToAdmin(long id) {
        this.restTemplate.put(Global.User_Service_Url + "/update/" + id + "/" + this.apiKey.getKey(),
                Object.class);
    }

    public void downgradeToUser(long id) {
        this.restTemplate.put(Global.User_Service_Url + "/downgrade/" + id + "/" + this.apiKey.getKey(),
                Object.class);
    }
}
