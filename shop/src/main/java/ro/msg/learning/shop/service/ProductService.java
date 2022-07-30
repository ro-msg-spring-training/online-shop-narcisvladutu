package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.entity_exception.ProductCategoryException;
import ro.msg.learning.shop.exception.entity_exception.ProductException;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final String ERROR_MESSAGE = "product not found for the id ";
    private final ProductRepository productRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
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
            productRepository.save(product);
        } else {
            throw (new ProductCategoryException(ERROR_MESSAGE+ product.getId()));
        }
    }
}
