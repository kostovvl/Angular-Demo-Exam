package examapi.contentservice.domain.dto;

import java.util.List;

public class AllCategories {

    private List<CategoryDto> all;

    public AllCategories() {

    }

    public AllCategories(List<CategoryDto> all) {
        this.all = all;
    }

    public List<CategoryDto> getAll() {
        return all;
    }

    public void setAll(List<CategoryDto> all) {
        this.all = all;
    }
}
