package ro.msg.learning.shop.service.strategy_utils;

import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.StockService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SingleLocationStrategy extends StrategyService {
    public SingleLocationStrategy(final LocationService locationService,
                                  final OrderDetailRepository orderDetailRepository,
                                  final StockService stockService) {
        this.locationService = locationService;
        this.orderDetailRepository = orderDetailRepository;
        this.stockService = stockService;
    }

    private final LocationService locationService;

    @Override
    public List<OrderDetail> findOrderDetailsLocation(List<OrderDetail> orderDetails) {
        List<OrderDetail> orderDetailsWithLocation = new ArrayList<>();

        final Location shippingLocation = locationService.getSingleShippingLocation(orderDetails);

        for (final OrderDetail orderDetail : orderDetails) {
            final Integer quantity = orderDetail.getQuantity();
            orderDetailsWithLocation.add(new OrderDetail(null, orderDetail.getProduct(), shippingLocation, quantity));
        }

        return orderDetailsWithLocation;
    }
}
