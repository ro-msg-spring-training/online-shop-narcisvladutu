package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.ProductCategoryDto;
import ro.msg.learning.shop.model.ProductCategory;
import ro.msg.learning.shop.repository.ProductCategoryRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public void saveProductCategory(ProductCategoryDto productCategoryDto) {
        productCategoryRepository.save(new ProductCategory(productCategoryDto.getName(),
                productCategoryDto.getDescription(), new HashSet<>()));
    }

    public List<ProductCategory> findAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    public Optional<ProductCategory> getProductCategoryById(final Integer id) {
        return productCategoryRepository.findById(id);
    }

    public void deleteProductCategory(Integer categoryId) {
        if (productCategoryRepository.existsById(categoryId)) {
            productCategoryRepository.deleteById(categoryId);
        }
    }
}

