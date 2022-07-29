package ro.msg.learning.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.ProductDto;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.model.Supplier;

import java.math.BigDecimal;
import java.util.HashSet;

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
        String image = product.getImageUrl();

        return new ProductDto(id, name, description, price, weight, category.getId(), category.getName(),
                category.getDescription(), supplier.getId(), supplier.getName(), image);
    }

    public Product toProduct(ProductDto productDto) {
        return new Product(productDto.getName(), productDto.getDescription(),
                productDto.getPrice(), productDto.getWeight(), new ProductCategory(productDto.getProductCategoryName(),
                productDto.getProductCategoryDescription(), new HashSet<>()), new Supplier(productDto.getSupplierName()),
                productDto.getImageUrl());
    }
}
