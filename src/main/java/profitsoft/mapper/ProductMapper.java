package profitsoft.mapper;

import org.springframework.stereotype.Component;
import profitsoft.dto.request.ProductRequestDto;
import profitsoft.dto.response.ProductResponseDto;
import profitsoft.model.Product;
import profitsoft.service.CategoryService;

@Component
public class ProductMapper {
    private final CategoryService categoryService;

    public ProductMapper(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Product mapToProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setCategory(categoryService.get(productRequestDto.getCategoryId()));
        product.setTitle(productRequestDto.getTitle());
        product.setPrice(productRequestDto.getPrice());
        return product;
    }

    public ProductResponseDto mapToDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setId(product.getId());
        productResponseDto.setTitle(product.getTitle());
        productResponseDto.setCategoryId(product.getCategory().getId());
        return productResponseDto;
    }
}
