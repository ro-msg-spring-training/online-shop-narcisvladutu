package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.entity_exception.ProductCategoryException;
import ro.msg.learning.shop.exception.entity_exception.ProductException;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.ProductCategoryRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final String ERROR_MESSAGE = "product not found for the id ";
    private final ProductRepository productRepository;

    private final SupplierRepository supplierRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public void saveProduct(Product product) {
        save(product);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(final Integer id) {
        return Optional.ofNullable(productRepository.findById(id).orElseThrow(
                () -> new ProductException(ERROR_MESSAGE + id)));
    }

    public void deleteProduct(Integer productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        } else {
            throw (new ProductCategoryException(ERROR_MESSAGE + productId));
        }
    }

    public void updateProduct(final Product product) {
        if (productRepository.existsById(product.getId())) {
            save(product);
        } else {
            throw (new ProductException(ERROR_MESSAGE + product.getId()));
        }
    }

    private void save(Product product) {
        Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(product.getCategory().getId());
        Optional<Supplier> supplierOptional = supplierRepository.findById(product.getSupplier().getId());
        if (productCategoryOptional.isPresent() && supplierOptional.isPresent()) {
            ProductCategory productCategory = productCategoryOptional.get();
            Supplier supplier = supplierOptional.get();
            product.setCategory(productCategory);
            product.setSupplier(supplier);
            productRepository.save(product);
        } else {
            throw new ProductException("invalid arguments for product with the id" + product.getId());
        }
    }
}
