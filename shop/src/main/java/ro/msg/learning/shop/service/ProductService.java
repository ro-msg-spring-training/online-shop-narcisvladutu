package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.ProductDtoSave;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.ProductCategoryRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.SupplierRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SupplierRepository supplierRepository;

    public void saveProduct(ProductDtoSave productDtoSave) {
        ProductCategory productCategory = productCategoryRepository.getReferenceById(productDtoSave.getProductCategoryId());
        Supplier supplier = supplierRepository.getReferenceById(productDtoSave.getSupplierId());

        var product = new Product(
                productDtoSave.getName(),
                productDtoSave.getDescription(),
                productDtoSave.getPrice(),
                productDtoSave.getWeight(),
                productCategory,
                supplier,
                productDtoSave.getImageUrl()
        );
        productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
