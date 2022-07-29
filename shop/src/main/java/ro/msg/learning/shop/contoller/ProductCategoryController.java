package ro.msg.learning.shop.contoller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
        this.productCategoryService.saveProductCategory(productCategory);
    }

    @GetMapping("/categories")
    public List<ProductCategoryDto> findAllProductCategories() {
        return this.productCategoryService.findAllProductCategories().stream().map(productCategoryMapper::toDto).toList();
    }
}
