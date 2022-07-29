package ro.msg.learning.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.ProductCategoryDto;
import ro.msg.learning.shop.model.ProductCategory;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class ProductCategoryMapper {
    public ProductCategoryDto toDto(ProductCategory productCategory) {
        Integer id = productCategory.getId();
        String name = productCategory.getName();
        String description = productCategory.getDescription();

        return new ProductCategoryDto(id, name, description);
    }

    public ProductCategory toProductCategory(ProductCategoryDto productCategoryDto) {
        return new ProductCategory(productCategoryDto.getName(), productCategoryDto.getDescription(), new HashSet<>());
    }
}
