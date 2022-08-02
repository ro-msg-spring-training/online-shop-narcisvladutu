package ro.msg.learning.shop.contoller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.OrderDtoSave;
import ro.msg.learning.shop.exception.entity_exception.CustomerException;
import ro.msg.learning.shop.mapper.OrderDetailMapper;
import ro.msg.learning.shop.mapper.OrderMapper;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.service.CustomerService;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;

    @PostMapping("/orders")
    @ResponseBody
    public Order saveOrder(@RequestBody final OrderDtoSave orderDtoSave) {
        Optional<Customer> customer = customerService.findCustomerById(orderDtoSave.getCustomerId());
        if (customer.isPresent()) {
            Order order = orderMapper.toOrder(orderDtoSave, customer.get());
            List<OrderDetail> orderDetailList = orderDtoSave.getOrderDetailDtoSaveList().stream().map(orderDetailDtoSave -> {
                Product product = productService.findProductById(orderDetailDtoSave.getProductId()).orElseThrow();
                return orderDetailMapper.toOrderDetail(orderDetailDtoSave, product);
            }).toList();
            return orderService.saveOrder(order, orderDetailList);
        } else {
            throw new CustomerException("customer not found!");
        }
    }
}
