package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.ProductCategoryException;
import ro.msg.learning.shop.exception.ProductException;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(final Integer id) {
        return Optional.ofNullable(productRepository.findById(id).orElseThrow(
                () -> new ProductException("product not found for the id " + id)));
    }

    public void deleteProduct(Integer productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        } else {
            throw (new ProductCategoryException("product not found for the id " + productId));
        }
    }

    public void updateProduct(final Product product) {
        productRepository.save(product);
    }
}
