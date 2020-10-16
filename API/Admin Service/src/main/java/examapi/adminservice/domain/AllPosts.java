package examapi.adminservice.domain;

import java.util.List;

public class AllPosts {

    private List<Post> all;

    public AllPosts() {

    }

    public AllPosts(List<Post> all) {
        this.all = all;
    }

    public List<Post> getAll() {
        return all;
    }

    public void setAll(List<Post> all) {
        this.all = all;
    }
}
