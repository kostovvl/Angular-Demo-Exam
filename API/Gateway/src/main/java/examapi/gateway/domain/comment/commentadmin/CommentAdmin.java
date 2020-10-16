package examapi.gateway.domain.comment.commentadmin;

public class CommentAdmin {

    private  long id;
    private String creatorName;

    public CommentAdmin() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
