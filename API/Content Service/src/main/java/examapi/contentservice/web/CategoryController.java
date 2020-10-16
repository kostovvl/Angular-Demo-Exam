package examapi.contentservice.web;

import examapi.contentservice.domain.dto.AllCategories;
import examapi.contentservice.domain.dto.CategoryDto;
import examapi.contentservice.innerSecurity.ApiKey;
import examapi.contentservice.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ApiKey apiKey;

    public CategoryController(CategoryService categoryService, ApiKey apiKey) {
        this.categoryService = categoryService;
        this.apiKey = apiKey;
    }

    @PostMapping("/create/{apiKey}")
    public ResponseEntity<?> createCategory(@PathVariable(name = "apiKey") String apiKey
                                           ,@RequestBody CategoryDto categoryDto) {
        try {
            this.apiKey.checkKey(apiKey);
            return new ResponseEntity<>(this.categoryService.addCategory(categoryDto), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(categoryDto, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/all/{apiKey}")
    public ResponseEntity<?> getAll(@PathVariable(name = "apiKey") String apiKey){
        this.apiKey.checkKey(apiKey);
        AllCategories result = new AllCategories(this.categoryService.getAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update/{categoryId}/{apiKey}")
    public ResponseEntity<?> updateCategory(@PathVariable(name = "categoryId") long categoryId,
                                            @PathVariable(name = "apiKey") String apiKey
                                            ,@RequestBody CategoryDto updateCategoryDto) {
        try {
            this.apiKey.checkKey(apiKey);
            this.categoryService.updateCategory(categoryId, updateCategoryDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/{categoryId}/{apiKey}")
    public ResponseEntity<?> deleteCategory(@PathVariable(name = "categoryId") long categoryId
                                            ,@PathVariable(name = "apiKey") String apiKey) {
        this.apiKey.checkKey(apiKey);
        this.categoryService.deleteCategory(categoryId);
       return new ResponseEntity<>( HttpStatus.OK);
    }

}
