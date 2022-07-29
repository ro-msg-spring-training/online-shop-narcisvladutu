package ro.msg.learning.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.SupplierDto;
import ro.msg.learning.shop.model.Supplier;

@Component
@RequiredArgsConstructor
public class SupplierMapper {
    public SupplierDto toDto(Supplier supplier) {
        Integer id = supplier.getId();
        String name = supplier.getName();
        return new SupplierDto(id, name);
    }

    public Supplier toSupplier(SupplierDto supplierDto) {
        return new Supplier(supplierDto.getName());
    }
}
