package ro.msg.learning.shop.contoler;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.ProductCategoryDto;
import ro.msg.learning.shop.mapper.ProductCategoryMapper;
import ro.msg.learning.shop.service.ProductCategoryService;

import java.util.List;

@RestController
public class ProductCategoryControler {
    private final ProductCategoryService productService;
    private final ProductCategoryMapper productCategoryMapper;

    public ProductCategoryControler(ProductCategoryService productService, ProductCategoryMapper productCategoryMapper) {
        this.productService = productService;
        this.productCategoryMapper = productCategoryMapper;
    }

    @PostMapping("/products/categories")
    public void saveProductCategory(@RequestBody ProductCategoryDto productCategory) {
        this.productService.saveProductCategory(productCategoryMapper.toProductCategory(productCategory));
    }

    @GetMapping("/products/categories")
    public List<ProductCategoryDto> findAllProductCategories() {
        return this.productService.findAllProductCategories().stream().map(productCategoryMapper::toDto).toList();
    }
}
