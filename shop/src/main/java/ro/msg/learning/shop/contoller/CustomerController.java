package ro.msg.learning.shop.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.CustomerDto;
import ro.msg.learning.shop.dto.CustomerDtoSave;
import ro.msg.learning.shop.mapper.CustomerMapper;
import ro.msg.learning.shop.service.CustomerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping("/customers")
    public void saveCustomer(@RequestBody CustomerDtoSave customerDtoSave) {
        this.customerService.saveCustomer(customerMapper.toCustomer(customerDtoSave));
    }

    @GetMapping("/customers")
    public List<CustomerDto> findAllCustomers() {
        return this.customerService.findAllCustomers().stream().map(customerMapper::toDto).toList();
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/customers/{id}")
    public CustomerDto findCustomerById(@PathVariable Integer id) {
        return customerService.findCustomerById(id).map(customerMapper::toDto).orElseThrow();
    }
}
