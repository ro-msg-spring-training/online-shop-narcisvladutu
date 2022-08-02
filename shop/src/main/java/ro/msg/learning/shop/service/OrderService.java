package ro.msg.learning.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.entity_exception.OrderDetailException;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.service.strategy_utils.StrategyService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor

public class OrderService {
    private final StrategyService strategyService;
    private final OrderRepository orderRepository;

    @Transactional
    public void saveOrder(Order order, List<OrderDetail> orderDetails) {
        if (orderDetails.isEmpty()) {
            throw new OrderDetailException("put some products in your shopping cart");
        }

        orderRepository.save(order);

        List<OrderDetail> orderDetailsWithLocation = strategyService.findOrderDetailsLocation(orderDetails);
        strategyService.addOrderToOrderDetails(order, orderDetailsWithLocation);
    }
}
