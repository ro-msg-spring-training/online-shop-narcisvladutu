package ro.msg.learning.shop.service.strategy_utils;

import ro.msg.learning.shop.dto.OrderDtoSave;
import ro.msg.learning.shop.model.Order;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.service.StockService;

import java.util.List;

public abstract class StrategyService {
    protected OrderDetailRepository orderDetailRepository;

    public abstract List<OrderDetail> generateOrderDetails(OrderDtoSave orderDtoSave);

    public abstract String getStrategy();

    protected StockService stockService;

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
