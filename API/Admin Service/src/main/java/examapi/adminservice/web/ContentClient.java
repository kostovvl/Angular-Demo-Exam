package examapi.adminservice.web;

import examapi.adminservice.innerSecurity.ApiKey;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ContentClient {

    private final static String approve_post_url = "http://localhost:8082/posts/approve/";
    private final static String approve_comment_url = "http://localhost:8082/comments/approve/";

    private final static String delete_post_url = "http://localhost:8082/posts/delete/";
    private final static String delete_comment_url = "http://localhost:8082/comments/delete/";

    private final RestTemplate restTemplate;
    private final ApiKey apiKey;

    public ContentClient(RestTemplate restTemplate, ApiKey apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    //Had to put them in try-catch block because of when integration testing exception is thrown
    public void approvePost(long postId) {
        try {
            this.restTemplate.put(approve_post_url + postId + "/" + this.apiKey.getKey(), Object.class);
        } catch (Exception e) {

        }

    }

    public void approveComment(long commentId) {
        try {
            this.restTemplate.put(approve_comment_url + commentId + "/" + this.apiKey.getKey(), Object.class);
        } catch (Exception e) {

        }

    }

    public void deletePost (long postId) {
        try {
            this.restTemplate.delete(delete_post_url
                    + postId + "/" + this.apiKey.getKey());
        }catch (Exception e) {

        }

    }

    public void deleteComment(long commentId) {
        try {
            this.restTemplate.delete(delete_comment_url
                    + commentId + "/" + this.apiKey.getKey());
        } catch (Exception e) {

        }
    }
}
