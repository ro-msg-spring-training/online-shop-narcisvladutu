package ro.msg.learning.shop.contoler;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.service.ProductCategoryService;

import java.util.List;

@RestController
public class ProductCategoryControler {
    private final ProductCategoryService productService;

    public ProductCategoryControler(ProductCategoryService productService) {
        this.productService = productService;
    }

    @PostMapping("/products/categories")
    public void saveProductCategory(@RequestBody ProductCategory productCategory) {
        this.productService.saveProductCategory(productCategory);
    }

    @GetMapping("/products/categories")
    public List<ProductCategory> findAllProductCategories() {
        return this.productService.findAllProductCategories();
    }
}
