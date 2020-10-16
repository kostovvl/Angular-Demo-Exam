package examapi.gateway.domain.comment.commentadmin;

import java.util.List;

public class CommentAdminContainer {

    private List<CommentAdmin> all;

    public CommentAdminContainer() {
    }

    public CommentAdminContainer(List<CommentAdmin> comments) {
        this.all = comments;
    }

    public List<CommentAdmin> getAll() {
        return all;
    }

    public void setAll(List<CommentAdmin> all) {
        this.all = all;
    }
}
