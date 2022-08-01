package ro.msg.learning.shop.contoller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.ProductCategoryDto;
import ro.msg.learning.shop.mapper.ProductCategoryMapper;
import ro.msg.learning.shop.service.ProductCategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;
    private final ProductCategoryMapper productCategoryMapper;

    @PostMapping("/categories")
    public void saveProductCategory(@RequestBody ProductCategoryDto productCategory) {
        this.productCategoryService.saveProductCategory(productCategoryMapper.toProductCategory(productCategory));
    }

    @GetMapping("/categories")
    public List<ProductCategoryDto> findAllProductCategories() {
        return this.productCategoryService.findAllProductCategories().stream().map(productCategoryMapper::toDto).toList();
    }

    @DeleteMapping("/categories/{id}")
    public void deleteProductCategory(@PathVariable Integer id) {
        productCategoryService.deleteProductCategory(id);
    }

    @GetMapping("/categories/{id}")
    public ProductCategoryDto findProductCategoryById(@PathVariable Integer id) {
        return productCategoryService.findProductCategoryById(id).map(productCategoryMapper::toDto).orElseThrow();
    }
}
