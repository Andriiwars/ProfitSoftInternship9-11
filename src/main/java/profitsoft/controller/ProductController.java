package profitsoft.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import profitsoft.dto.request.ProductRequestDto;
import profitsoft.dto.response.ProductResponseDto;
import profitsoft.mapper.ProductMapper;
import profitsoft.model.Product;
import profitsoft.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/{id}")
    public ProductResponseDto get(@PathVariable Long id) {
        return productMapper.mapToDto(productService.get(id));
    }

    @GetMapping("/getAll")
    public List<Product> getAll() {
        return productService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponseDto save(@RequestBody ProductRequestDto requestDto) {
        return productMapper.mapToDto(productService
                .save(productMapper.mapToProduct(requestDto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id,
                                     @RequestBody ProductRequestDto requestDto) {
        Product product = productMapper.mapToProduct(requestDto);
        product.setId(id);
        return productMapper.mapToDto(productService.save(product));
    }

    @GetMapping("/by-price")
    public List<ProductResponseDto> getAllByPriceBetween(@RequestParam BigDecimal from,
                                                         @RequestParam BigDecimal to) {
        return productService.findAllByPriceBetween(from, to).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-title-and-by-price")
    public List<ProductResponseDto> getProductsByTitleAndPrice(@RequestParam String title,
                                                       @RequestParam BigDecimal price) {
        return productService.findProductsByTitleAndPrice(title,price).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
