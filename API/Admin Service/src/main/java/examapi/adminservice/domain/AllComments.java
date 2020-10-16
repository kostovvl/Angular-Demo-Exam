package examapi.adminservice.domain;

import java.util.List;

public class AllComments {

    private List<Comment> all;

    public AllComments() {

    }

    public AllComments(List<Comment> all) {
        this.all = all;
    }

    public List<Comment> getAll() {
        return all;
    }

    public void setAll(List<Comment> all) {
        this.all = all;
    }
}
