package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.entity_exception.CustomerException;
import ro.msg.learning.shop.exception.entity_exception.ProductCategoryException;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private static final String ERROR_MESSAGE = "category not found for the id ";
    private final CustomerRepository customerRepository;

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findCustomerById(final Integer id) {
        return Optional.ofNullable(customerRepository.findById(id).orElseThrow(
                () -> new CustomerException(ERROR_MESSAGE + id)));
    }

    public void deleteCustomer(Integer customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
        } else {
            throw (new ProductCategoryException(ERROR_MESSAGE + customerId));
        }
    }
}