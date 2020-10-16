package examapi.gateway.web.client;

import examapi.gateway.configuration.Global;
import examapi.gateway.domain.comment.commentuser.Comment;
import examapi.gateway.domain.comment.commentuser.CommentContainer;
import examapi.gateway.innerSecurity.ApiKey;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CommentClient {

    private final RestTemplate restTemplate;
    private final ApiKey apiKey;

    public CommentClient(RestTemplate restTemplate, ApiKey apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }


    public Comment create(Comment comment) {
     return  this.restTemplate.postForObject(Global.Content_Service_Url + "comments/create/" +
                this.apiKey.getKey(), comment, Comment.class);
    }

    public CommentContainer getForPost(long postId) {
        return  this.restTemplate.getForObject(Global.Content_Service_Url + "comments/getForPost/" +
              postId + "/" +  this.apiKey.getKey(), CommentContainer.class);
    }


    public void update(long commentId, Comment comment) {
         this.restTemplate.put(Global.Content_Service_Url + "comments/update/" +
                commentId + "/" + this.apiKey.getKey(), comment);
    }

    public Comment getById(long id) {
        return  this.restTemplate.getForObject(Global.Content_Service_Url + "/comments/getById/"
        + id + "/" + this.apiKey.getKey(), Comment.class);
        }

    public void delete(long commentId) {
        this.restTemplate.delete(Global.Content_Service_Url + "comments/delete/"
        + commentId + "/" + this.apiKey.getKey());
    }
}
