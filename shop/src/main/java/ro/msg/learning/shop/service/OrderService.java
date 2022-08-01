package ro.msg.learning.shop.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.OrderDetailDtoSave;
import ro.msg.learning.shop.dto.OrderDtoSave;
import ro.msg.learning.shop.exception.entity_exception.OrderDetailException;
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.service.strategy_utils.StrategyService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final StrategyService strategyService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public void saveOrder(final OrderDtoSave orderDtoSave) {
        validateShoppingCartNotEmpty(orderDtoSave.getOrderDetailDtoSaveList());

        Customer customer = customerRepository.findById(orderDtoSave.getCustomerId()).orElseThrow();

        final Order orderCreated = new Order(customer, orderDtoSave.getCreatedAt(), new Address(
                orderDtoSave.getAddressCountry(), orderDtoSave.getAddressCity(), orderDtoSave.getAddressCounty(),
                orderDtoSave.getAddressStreetAddress()
        ));

        orderRepository.save(orderCreated);

        final List<OrderDetail> orderDetails = strategyService.generateOrderDetails(orderDtoSave);
        strategyService.addOrderToOrderDetails(orderCreated, orderDetails);
    }

    private void validateShoppingCartNotEmpty(List<OrderDetailDtoSave> orderDetailDtoSaveList) {
        if (orderDetailDtoSaveList.isEmpty()) {
            throw new OrderDetailException();
        }
    }
}
