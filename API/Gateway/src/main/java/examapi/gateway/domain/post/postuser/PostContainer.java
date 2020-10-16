package examapi.gateway.domain.post.postuser;

import java.util.List;

public class PostContainer {

    private List<Post> all;

    public PostContainer() {
    }

    public PostContainer(List<Post> posts) {
        this.all = posts;
    }

    public List<Post> getAll() {
        return all;
    }

    public void setAll(List<Post> all) {
        this.all = all;
    }
}
