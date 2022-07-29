package ro.msg.learning.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.ProductDto;
import ro.msg.learning.shop.dto.ProductDtoSave;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.model.Supplier;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    public ProductDto toDto(Product product) {
        Integer id = product.getId();
        String name = product.getName();
        String description = product.getDescription();
        BigDecimal price = product.getPrice();
        Double weight = product.getWeight();
        ProductCategory category = product.getCategory();
        Supplier supplier = product.getSupplier();
        return new ProductDto(id, name, description, price, weight, category.getName(), supplier.getName());
    }

    public Product toProduct(ProductDtoSave productDtoSave, ProductCategory productCategory, Supplier supplier) {
        return new Product(
                productDtoSave.getName(),
                productDtoSave.getDescription(),
                productDtoSave.getPrice(),
                productDtoSave.getWeight(),
                productCategory,
                supplier,
                productDtoSave.getImageUrl()
        );
    }
}
