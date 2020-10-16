package examapi.gateway.web.controller;

import examapi.gateway.domain.category.Category;
import examapi.gateway.web.client.CategoryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryClient categoryClient;

    public CategoryController(CategoryClient categoryClient) {
        this.categoryClient = categoryClient;
    }



    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(this.categoryClient.all(), HttpStatus.OK);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable(name = "categoryId") long categoryId,
                                            @RequestBody Category category) {

        try {
            boolean response = this.categoryClient.update(categoryId ,category);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable(name = "categoryId") long categoryId) {
       this.categoryClient.delete(categoryId);

            return new ResponseEntity<>(true, HttpStatus.OK);

    }
}
