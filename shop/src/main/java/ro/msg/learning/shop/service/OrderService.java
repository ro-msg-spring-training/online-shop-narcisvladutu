package ro.msg.learning.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.entity_exception.CustomerException;
import ro.msg.learning.shop.exception.entity_exception.OrderDetailException;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.service.strategy_utils.StrategyService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class OrderService {
    private final StrategyService strategyService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order saveOrder(Order order, List<OrderDetail> orderDetails) {
        if (orderDetails.isEmpty()) {
            throw new OrderDetailException("put some products in your shopping cart");
        }

        Optional<Customer> customer = customerRepository.findById(order.getCustomer().getId());
        if (customer.isPresent()) {
            order.setCustomer(customer.get());
            orderRepository.save(order);

            orderDetails.forEach(orderDetail -> {
                Product product = productRepository.findById(orderDetail.getProduct().getId()).orElseThrow();
                orderDetail.setProduct(product);
            });

            List<OrderDetail> orderDetailsWithLocation = strategyService.findOrderDetailsLocation(orderDetails);
            strategyService.addOrderToOrderDetails(order, orderDetailsWithLocation);

            return order;
        } else {
            throw new CustomerException("customer not found!");
        }
    }
}
