package profitsoft;

import java.math.BigDecimal;
import java.util.List;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import profitsoft.dto.request.ProductRequestDto;
import profitsoft.dto.response.ProductResponseDto;
import profitsoft.mapper.ProductMapper;
import profitsoft.model.Product;
import profitsoft.service.ProductService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void returnProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("toyota");
        product.setPrice(BigDecimal.valueOf(1000));
        product.setCategory(null);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(1L);
        productResponseDto.setTitle("toyota");
        productResponseDto.setPrice(BigDecimal.valueOf(1000));

        Mockito.when(productService.get(1L)).thenReturn(product);
        Mockito.when(productMapper.mapToDto(product)).thenReturn(productResponseDto);

        RestAssuredMockMvc.when()
                .get("/products/1")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("title", Matchers.equalTo("toyota"))
                .body("price", Matchers.equalTo(1000))
                .body("category", Matchers.equalTo(null));
    }

    @Test
    void returnAllProducts() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("toyota");
        product.setPrice(BigDecimal.valueOf(1000));
        product.setCategory(null);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(1L);
        productResponseDto.setTitle("toyota");
        productResponseDto.setPrice(BigDecimal.valueOf(1000));
        productResponseDto.setCategoryId(null);


        Mockito.when(productService.getAll()).thenReturn(List.of(product));
        Mockito.when(productMapper.mapToDto(product)).thenReturn(productResponseDto);

        RestAssuredMockMvc.when()
                .get("/products/getAll")
                .then()
                .statusCode(200)
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].title", Matchers.equalTo("toyota"))
                .body("[0].price", Matchers.equalTo(1000))
                .body("[0].category", Matchers.equalTo(null));
    }

    @Test
    void createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("toyota");
        product.setPrice(BigDecimal.valueOf(1000));
        product.setCategory(null);

        Product productWithoutId = new Product();
        productWithoutId.setTitle("toyota");
        productWithoutId.setPrice(BigDecimal.valueOf(1000));
        productWithoutId.setCategory(null);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(1L);
        productResponseDto.setTitle("toyota");
        productResponseDto.setPrice(BigDecimal.valueOf(1000));
        productResponseDto.setCategoryId(null);

        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setTitle("toyota");
        productRequestDto.setPrice(BigDecimal.valueOf(1000));
        productRequestDto.setCategoryId(null);

        Mockito.when(productMapper.mapToProduct(productRequestDto)).thenReturn(productWithoutId);
        Mockito.when(productService.save(productWithoutId)).thenReturn(product);
        Mockito.when(productMapper.mapToDto(product)).thenReturn(productResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(productRequestDto)
                .when()
                .post("/products")
                .then()
                .statusCode(201)
                .body("id", Matchers.equalTo(1))
                .body("title", Matchers.equalTo("toyota"))
                .body("price", Matchers.equalTo(1000))
                .body("category", Matchers.equalTo(null));
    }

    @Test
    void updateProduct() {
        Product product = new Product();
        product.setId(2L);
        product.setTitle("toyota");
        product.setPrice(BigDecimal.valueOf(1000));
        product.setCategory(null);

        Product productWithoutId = new Product();
        productWithoutId.setTitle("toyota");
        productWithoutId.setPrice(BigDecimal.valueOf(1000));
        productWithoutId.setCategory(null);

        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setTitle("toyota");
        productRequestDto.setPrice(BigDecimal.valueOf(1000));
        productRequestDto.setCategoryId(null);


        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(2L);
        productResponseDto.setTitle("toyota");
        productResponseDto.setPrice(BigDecimal.valueOf(1000));
        productResponseDto.setCategoryId(null);

        Mockito.when(productMapper.mapToProduct(productRequestDto)).thenReturn(productWithoutId);
        Mockito.when(productService.save(product)).thenReturn(product);
        Mockito.when(productMapper.mapToDto(product)).thenReturn(productResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(productRequestDto)
                .when()
                .put("/products/2")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(2))
                .body("title", Matchers.equalTo("toyota"))
                .body("price", Matchers.equalTo(1000))
                .body("category", Matchers.equalTo(null));
    }

    @Test
    void deleteProduct() {
        RestAssuredMockMvc.given().delete("/products/1");
        Mockito.verify(productService).delete(1L);
    }
}
