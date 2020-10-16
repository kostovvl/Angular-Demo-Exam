package examapi.gateway.web.client;

import examapi.gateway.configuration.Global;
import examapi.gateway.domain.category.Category;
import examapi.gateway.domain.category.CategoryContainer;
import examapi.gateway.innerSecurity.ApiKey;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CategoryClient {

    private final RestTemplate restTemplate;
    private final ApiKey apiKey;

    public CategoryClient(RestTemplate restTemplate, ApiKey apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public Category createNew(Category category) {
        return this.restTemplate.postForObject(Global.Content_Service_Url + "/categories/create/" + this.apiKey.getKey(),
                category, Category.class);
    }

    public List<Category> all () {
        return this.restTemplate.getForObject(Global.Content_Service_Url + "categories/all/" + this.apiKey.getKey()
        , CategoryContainer.class).getAll();
    }

    public boolean update(long categoryId, Category updated) {
        try {
            this.restTemplate.put(Global.Content_Service_Url + "/categories/update/" + categoryId + "/" + this.apiKey.getKey(),
                    updated);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void delete(long categoryId) {

            this.restTemplate.delete(Global.Content_Service_Url + "/categories/delete/"
                    + categoryId + "/" + this.apiKey.getKey());

    }
}
