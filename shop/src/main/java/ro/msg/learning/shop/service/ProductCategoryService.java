package ro.msg.learning.shop.service;

import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.repository.ProductCategoryRepository;

import java.util.List;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategories;

    public ProductCategoryService(ProductCategoryRepository productCategories) {
        this.productCategories = productCategories;
    }

    public void saveProductCategory(ProductCategory category) {
        productCategories.save(category);
    }

    public List<ProductCategory> findAllProductCategories() {
        return productCategories.findAll();
    }
}

