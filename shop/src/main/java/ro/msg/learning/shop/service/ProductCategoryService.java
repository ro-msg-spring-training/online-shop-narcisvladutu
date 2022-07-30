package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.entity_exception.ProductCategoryException;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.repository.ProductCategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public void saveProductCategory(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }

    public List<ProductCategory> findAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    public Optional<ProductCategory> findProductCategoryById(final Integer id) {
        return Optional.ofNullable(productCategoryRepository.findById(id).orElseThrow(
                () -> new ProductCategoryException("category not found for the id " + id)));
    }

    public void deleteProductCategory(Integer categoryId) {
        if (productCategoryRepository.existsById(categoryId)) {
            productCategoryRepository.deleteById(categoryId);
        } else {
            throw (new ProductCategoryException("category not found for the id " + categoryId));
        }
    }
}

