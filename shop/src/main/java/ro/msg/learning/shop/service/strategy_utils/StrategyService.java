package ro.msg.learning.shop.service.strategy_utils;

import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.repository.OrderDetailRepository;

import java.util.List;

public abstract class StrategyService {
    protected OrderDetailRepository orderDetailRepository;

    public abstract List<OrderDetail> findOrderDetailsLocation(List<OrderDetail> orderDetails);
}
