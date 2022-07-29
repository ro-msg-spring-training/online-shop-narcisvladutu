package ro.msg.learning.shop.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.SupplierDto;
import ro.msg.learning.shop.mapper.SupplierMapper;
import ro.msg.learning.shop.service.SupplierService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;
    private final SupplierMapper supplierMapper;

    @PostMapping("/suppliers")
    public void saveProductCategory(@RequestBody SupplierDto supplierDto) {
        this.supplierService.saveSupplier(supplierMapper.toSupplier(supplierDto));
    }

    @GetMapping("/suppliers")
    public List<SupplierDto> findAllProductCategories() {
        return this.supplierService.findAllSuppliers().stream().map(supplierMapper::toDto).toList();
    }
}
