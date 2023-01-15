package profitsoft.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import profitsoft.dto.request.CategoryRequestDto;
import profitsoft.dto.response.CategoryResponseDto;
import profitsoft.mapper.CategoryMapper;
import profitsoft.model.Category;
import profitsoft.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getById(@PathVariable Long id) {
        return categoryMapper.mapToDto(categoryService.get(id));
    }

    @GetMapping("/getAll")
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponseDto add(@RequestBody CategoryRequestDto dto) {
        return categoryMapper.mapToDto(
                categoryService.save(categoryMapper.mapToCategory(dto)));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @PutMapping("/{id}")
    public CategoryResponseDto update(@PathVariable Long id,
                                      @RequestBody CategoryRequestDto dto) {
        Category category = categoryMapper.mapToCategory(dto);
        category.setId(id);
        return categoryMapper.mapToDto(categoryService.save(category));
    }
}
