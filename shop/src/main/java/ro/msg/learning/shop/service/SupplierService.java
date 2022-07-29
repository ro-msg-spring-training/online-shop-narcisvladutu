package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.SupplierDto;
import ro.msg.learning.shop.model.Supplier;
import ro.msg.learning.shop.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public void saveSupplier(SupplierDto supplierDto) {
        supplierRepository.save(new Supplier(supplierDto.getName()));
    }

    public List<Supplier> findAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getSupplierById(final Integer id) {
        return supplierRepository.findById(id);
    }

    public void deleteSupplier(Integer categoryId) {
        if (supplierRepository.existsById(categoryId)) {
            supplierRepository.deleteById(categoryId);
        }
    }
}
