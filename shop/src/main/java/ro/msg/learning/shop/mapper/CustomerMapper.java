package ro.msg.learning.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.CustomerDto;
import ro.msg.learning.shop.dto.CustomerDtoSave;
import ro.msg.learning.shop.model.Customer;

@Component
@RequiredArgsConstructor
public class CustomerMapper {
    public CustomerDto toDto(Customer customer) {
        Integer id = customer.getId();
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        String emailAddress = customer.getEmailAddress();
        return new CustomerDto(id, firstName, lastName, emailAddress);
    }

    public Customer toCustomer(CustomerDtoSave customerDtoSave) {
        return new Customer(customerDtoSave.getFirstName(), customerDtoSave.getFirstName(),
                customerDtoSave.getUsername(), customerDtoSave.getPassword(), customerDtoSave.getEmailAddress());
    }
}
