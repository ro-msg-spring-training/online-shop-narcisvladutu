package ro.msg.learning.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.OrderDto;
import ro.msg.learning.shop.dto.OrderDtoSave;
import ro.msg.learning.shop.model.*;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    public OrderDto toDto(Order order) {
        return new OrderDto(order.getId(), order.getCustomer().getUsername(), order.getCreatedAt(),
                order.getAddress().getCountry(), order.getAddress().getCity(), order.getAddress().getCounty(),
                order.getAddress().getStreetAddress());
    }

    public Order toOrder(OrderDtoSave orderDtoSave, Customer customer) {
        return new Order(customer, orderDtoSave.getCreatedAt(), new Address(orderDtoSave.getAddressCountry(),
                orderDtoSave.getAddressCity(), orderDtoSave.getAddressCounty(), orderDtoSave.getAddressStreetAddress()));
    }
}
