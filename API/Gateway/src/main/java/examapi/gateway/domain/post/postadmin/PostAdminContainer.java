package examapi.gateway.domain.post.postadmin;

import java.util.List;

public class PostAdminContainer {

    private List<PostAdmin> all;

    public PostAdminContainer() {
    }

    public List<PostAdmin> getAll() {
        return all;
    }

    public void setAll(List<PostAdmin> all) {
        this.all = all;
    }
}
