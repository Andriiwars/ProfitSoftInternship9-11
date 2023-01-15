package profitsoft.service;

import java.util.List;
import profitsoft.model.Category;

public interface CategoryService {
    Category save(Category category);

    Category get(Long id);

    List<Category> getAll();

    void delete(Long id);
}
