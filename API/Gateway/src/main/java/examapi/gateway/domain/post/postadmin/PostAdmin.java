package examapi.gateway.domain.post.postadmin;

public class PostAdmin {

    long id;
    String title;

    public PostAdmin() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
