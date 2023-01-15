package profitsoft;

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
import profitsoft.dto.request.CategoryRequestDto;
import profitsoft.dto.response.CategoryResponseDto;
import profitsoft.mapper.CategoryMapper;
import profitsoft.model.Category;
import profitsoft.service.CategoryService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {
    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryMapper categoryMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void returnCategoryById() {
        Category category = new Category();
        category.setId(1L);
        category.setName("cars");

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(1L);
        categoryResponseDto.setName("cars");

        Mockito.when(categoryService.get(1L)).thenReturn(category);
        Mockito.when(categoryMapper.mapToDto(category)).thenReturn(categoryResponseDto);

        RestAssuredMockMvc.when()
                .get("/categories/1")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("cars"));
    }
    @Test
    void returnAllCategories() {
        Category category = new Category();
        category.setId(1L);
        category.setName("cars");

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(1L);
        categoryResponseDto.setName("cars");

        Mockito.when(categoryService.getAll()).thenReturn(List.of(category));
        Mockito.when(categoryMapper.mapToDto(category)).thenReturn(categoryResponseDto);

        RestAssuredMockMvc.when()
                .get("categories/getAll")
                .then()
                .statusCode(200)
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].name", Matchers.equalTo("cars"));
    }

    @Test
    void createCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("cars");

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(1L);
        categoryResponseDto.setName("cars");

        Category categoryWithoutId = new Category();
        categoryWithoutId.setName("cars");

        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setName("cars");

        Mockito.when(categoryMapper.mapToCategory(categoryRequestDto)).thenReturn(categoryWithoutId);
        Mockito.when(categoryService.save(categoryWithoutId)).thenReturn(category);
        Mockito.when(categoryMapper.mapToDto(category)).thenReturn(categoryResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(categoryRequestDto)
                .when()
                .post("/categories")
                .then()
                .statusCode(201)
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("cars"));
    }

    @Test
    void updateCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("cars");

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(1L);
        categoryResponseDto.setName("iphone");

        Category categoryWithoutId = new Category();
        categoryWithoutId.setName("cars");

        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setName("iphone");

        Mockito.when(categoryMapper.mapToCategory(categoryRequestDto)).thenReturn(categoryWithoutId);
        Mockito.when(categoryService.save(category)).thenReturn(category);
        Mockito.when(categoryMapper.mapToDto(category)).thenReturn(categoryResponseDto);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(categoryRequestDto)
                .when()
                .put("/categories/1")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("iphone"));
    }

    @Test
    void deleteCategory() {
        RestAssuredMockMvc.given().delete("/categories/1");
        Mockito.verify(categoryService).delete(1L);
    }
}