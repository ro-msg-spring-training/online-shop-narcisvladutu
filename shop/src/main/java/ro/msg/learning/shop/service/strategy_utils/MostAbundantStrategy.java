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
public class MostAbundantStrategy extends StrategyService {
    private final LocationService locationService;

    public MostAbundantStrategy(final LocationService locationService,
                                final OrderDetailRepository orderDetailRepository,
                                final StockService stockService) {
        this.locationService = locationService;
        this.orderDetailRepository = orderDetailRepository;
        this.stockService = stockService;
    }

    @Override
    public List<OrderDetail> findOrderDetailsLocation(List<OrderDetail> orderDetails) {
        List<OrderDetail> orderDetailsWithLocation = new ArrayList<>();
        for (final OrderDetail orderDetail : orderDetails) {
            final Integer productId = orderDetail.getProduct().getId();
            final Integer quantity = orderDetail.getQuantity();
            final Location shippingLocation = locationService.getProductMostAbundantShippingLocation(productId,
                    quantity);
            orderDetailsWithLocation.add(new OrderDetail(null, orderDetail.getProduct(), shippingLocation, quantity));
        }
        return orderDetailsWithLocation;
    }
}
