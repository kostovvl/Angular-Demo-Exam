package examapi.gateway.domain.category;

import java.util.List;

public class CategoryContainer {

    private List<Category> all;

    public CategoryContainer() {

    }

    public CategoryContainer(List<Category> all) {
        this.all = all;
    }

    public List<Category> getAll() {
        return all;
    }

    public void setAll(List<Category> all) {
        this.all = all;
    }
}
