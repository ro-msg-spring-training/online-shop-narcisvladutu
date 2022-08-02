package ro.msg.learning.shop.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.ProductDto;
import ro.msg.learning.shop.dto.ProductDtoSave;
import ro.msg.learning.shop.mapper.ProductMapper;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/products")
    public List<ProductDto> findAllProducts() {
        final List<Product> products = productService.findAllProducts();
        return products.stream().map(productMapper::toDto).toList();
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProduct(@RequestBody ProductDtoSave productDtoSave) {
        productService.saveProduct(productMapper.toProduct(productDtoSave));
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/products/{id}")
    public ProductDto findProductCategoryById(@PathVariable Integer id) {
        return productService.findProductById(id).map(productMapper::toDto).orElseThrow();
    }

    @PutMapping(value = "/products/{id}")
    public void updateProduct(@PathVariable("id") final Integer id, @RequestBody final ProductDtoSave productDtoSave) {
        final Product product = productMapper.toProduct(productDtoSave);
        product.setId(id);
        productService.updateProduct(product);
    }
}
