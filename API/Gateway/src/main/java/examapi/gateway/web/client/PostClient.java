package examapi.gateway.web.client;

import examapi.gateway.configuration.Global;
import examapi.gateway.domain.post.postuser.Post;
import examapi.gateway.domain.post.postuser.PostContainer;
import examapi.gateway.innerSecurity.ApiKey;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PostClient {

    private final RestTemplate restTemplate;
    private final ApiKey apiKey;

    public PostClient(RestTemplate restTemplate, ApiKey apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public Post create(Post post) {
        return this.restTemplate.postForObject(Global.Content_Service_Url + "posts/create/" +
                this.apiKey.getKey(), post, Post.class);
    }

    public void update(long postId, Post post) {
    this.restTemplate.put(Global.Content_Service_Url + "posts/update/" +
               postId + "/" + this.apiKey.getKey(), post);
    }

    public PostContainer all() {
        return this.restTemplate.getForObject(Global.Content_Service_Url + "posts/all/"
                 + this.apiKey.getKey(), PostContainer.class);
    }

    public PostContainer allForCategory (long categoryId) {
        return this.restTemplate.getForObject(Global.Content_Service_Url + "posts/byCategory/"
              + categoryId + "/"  + this.apiKey.getKey(), PostContainer.class);
    }

    public Post details(long postId) {
            return this.restTemplate.getForObject(Global.Content_Service_Url + "posts/details/"
                    + postId + "/"  + this.apiKey.getKey(), Post.class);
    }

    //approve methood - put

    public void delete (long postId) {
        this.restTemplate.delete(Global.Content_Service_Url + "posts/delete/"
        + postId + "/" + this.apiKey.getKey());
    }
}
