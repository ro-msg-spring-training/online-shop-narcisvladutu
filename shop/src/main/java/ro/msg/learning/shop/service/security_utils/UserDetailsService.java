package ro.msg.learning.shop.service.security_utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Credential;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsService {
    private final CustomerRepository customerRepository;

    public List<Credential> getUsernamesAndPasswords() {
        List<Customer> customers = customerRepository.findAll();
        List<Credential> credentials = new ArrayList<>();
        customers.forEach(customer -> credentials.add(new Credential(customer.getUsername(), customer.getPassword())));
        return credentials;
    }
}
