package ro.msg.learning.shop.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.ProductDto;
import ro.msg.learning.shop.dto.ProductDtoSave;
import ro.msg.learning.shop.mapper.ProductMapper;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.service.ProductCategoryService;
import ro.msg.learning.shop.service.ProductService;
import ro.msg.learning.shop.service.SupplierService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final SupplierService supplierService;
    private final ProductCategoryService productCategoryService;
    private final ProductMapper productMapper;

    @GetMapping("/products")
    public List<ProductDto> findAllProducts() {
        final List<Product> products = productService.findAllProducts();
        return products.stream().map(productMapper::toDto).toList();
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProduct(@RequestBody ProductDtoSave productDtoSave) {
        Optional<ProductCategory> productCategoryOptional = productCategoryService.getProductCategoryById(productDtoSave.getProductCategoryId());
        Optional<Supplier> supplierOptional = supplierService.getSupplierById(productDtoSave.getSupplierId());
        if (productCategoryOptional.isPresent() && supplierOptional.isPresent()) {
            ProductCategory productCategory = productCategoryOptional.get();
            Supplier supplier = supplierOptional.get();
            productService.saveProduct(productMapper.toProduct(productDtoSave, productCategory, supplier));
        }
    }
}
