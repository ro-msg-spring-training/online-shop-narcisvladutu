package ro.msg.learning.shop.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    public void saveSupplier(@RequestBody SupplierDto supplierDto) {
        this.supplierService.saveSupplier(supplierMapper.toSupplier(supplierDto));
    }

    @GetMapping("/suppliers")
    public List<SupplierDto> findAllSuppliers() {
        return this.supplierService.findAllSuppliers().stream().map(supplierMapper::toDto).toList();
    }

    @DeleteMapping("/suppliers/{id}")
    public void deleteSupplier(@PathVariable Integer id) {
        supplierService.deleteSupplier(id);
    }

    @GetMapping("/suppliers/{id}")
    public SupplierDto findSupplierById(@PathVariable Integer id) {
        return supplierService.findSupplierById(id).map(supplierMapper::toDto).orElseThrow();
    }
}
