package profitsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import profitsoft.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
