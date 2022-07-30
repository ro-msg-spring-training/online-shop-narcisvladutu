package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.entity_exception.ProductCategoryException;
import ro.msg.learning.shop.exception.entity_exception.SupplierException;
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private static final String ERROR_MESSAGE = "supplier not found for the id ";

    private final SupplierRepository supplierRepository;

    public void saveSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    public List<Supplier> findAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> findSupplierById(final Integer id) {
        return Optional.ofNullable(supplierRepository.findById(id).orElseThrow(
                () -> new SupplierException(ERROR_MESSAGE + id)));
    }

    public void deleteSupplier(Integer supplierID) {
        if (supplierRepository.existsById(supplierID)) {
            supplierRepository.deleteById(supplierID);
        } else {
            throw (new ProductCategoryException(ERROR_MESSAGE + supplierID));
        }
    }
}
