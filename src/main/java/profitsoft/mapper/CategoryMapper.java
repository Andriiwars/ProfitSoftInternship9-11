package profitsoft.mapper;

import org.springframework.stereotype.Component;
import profitsoft.dto.request.CategoryRequestDto;
import profitsoft.dto.response.CategoryResponseDto;
import profitsoft.model.Category;

@Component
public class CategoryMapper {
    public Category mapToCategory(CategoryRequestDto requestDto) {
        Category category = new Category();
        category.setName(requestDto.getName());
        return category;
    }

    public CategoryResponseDto mapToDto(Category category) {
        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setId(category.getId());
        responseDto.setName(category.getName());
        return responseDto;
    }
}
