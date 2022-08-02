package ro.msg.learning.shop.contoller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.OrderDtoSave;
import ro.msg.learning.shop.mapper.OrderDetailMapper;
import ro.msg.learning.shop.mapper.OrderMapper;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;

    @PostMapping("/orders")
    @ResponseBody
    public Order saveOrder(@RequestBody final OrderDtoSave orderDtoSave) {
        Order order = orderMapper.toOrder(orderDtoSave);
        List<OrderDetail> orderDetailList = orderDtoSave.getOrderDetailDtoSaveList().stream().map(orderDetailMapper::toOrderDetail).toList();
        return orderService.saveOrder(order, orderDetailList);
    }
}
