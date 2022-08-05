package ro.msg.learning.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.entity_exception.CustomerException;
import ro.msg.learning.shop.exception.entity_exception.OrderDetailException;
import ro.msg.learning.shop.exception.entity_exception.ProductException;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderDetailRepository;
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
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    protected StockService stockService;

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
                Optional<Product> product = productRepository.findById(orderDetail.getProduct().getId());
                if (product.isPresent()) {
                    orderDetail.setProduct(product.get());
                } else {
                    throw new ProductException("product not found");
                }
            });

            List<OrderDetail> orderDetailsWithLocation = strategyService.findOrderDetailsLocation(orderDetails);
            addOrderToOrderDetails(order, orderDetailsWithLocation);

            return order;
        } else {
            throw new CustomerException("customer not found!");
        }
    }

    public void addOrderToOrderDetails(final Order order, final List<OrderDetail> orderDetails) {
        orderDetails.forEach(orderDetail -> {
            orderDetail.setOrder(order);
            orderDetailRepository.save(orderDetail);
        });
        updateStocks(orderDetails);
    }

    private void updateStocks(final List<OrderDetail> orderDetails) {
        orderDetails.forEach(orderDetail -> stockService.updateStock(orderDetail.getLocation().getId(), orderDetail.getProduct().getId(), orderDetail.getQuantity()));
    }
}
