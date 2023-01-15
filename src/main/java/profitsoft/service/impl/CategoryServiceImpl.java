package profitsoft.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import profitsoft.model.Category;
import profitsoft.repository.CategoryRepository;
import profitsoft.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category get(Long id) {
        return categoryRepository.getById(id);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
