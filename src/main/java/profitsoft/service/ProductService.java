package profitsoft.service;

import java.math.BigDecimal;
import java.util.List;
import profitsoft.model.Product;

public interface ProductService {
    Product save(Product product);

    Product get(Long id);

    List<Product> getAll();

    void delete(Long id);

    List<Product> findAllByPriceBetween(BigDecimal from, BigDecimal to);

    List<Product> findAllByCategoryIn(List<Long> categories);

    List<Product> findProductsByTitleAndPrice(String title, BigDecimal price);
}
