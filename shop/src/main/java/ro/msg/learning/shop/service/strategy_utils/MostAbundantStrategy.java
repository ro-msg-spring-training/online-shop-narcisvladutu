package ro.msg.learning.shop.service.strategy_utils;

import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.OrderDetailDtoSave;
import ro.msg.learning.shop.dto.OrderDtoSave;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.ProductService;
import ro.msg.learning.shop.service.StockService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MostAbundantStrategy extends StrategyService {
    private final LocationService locationService;
    private final ProductService productService;


    public MostAbundantStrategy(final LocationService locationService, final ProductService productService,
                                final OrderDetailRepository orderDetailRepository,
                                final StockService stockService) {
        this.locationService = locationService;
        this.productService = productService;
        this.orderDetailRepository = orderDetailRepository;
        this.stockService = stockService;
    }

    @Override
    public List<OrderDetail> generateOrderDetails(OrderDtoSave orderDto) {
        final List<OrderDetail> orderDetails = new ArrayList<>();

        final List<OrderDetailDtoSave> orderDetailDtoList = orderDto.getOrderDetailDtoSaveList();


        for (final OrderDetailDtoSave orderDetailDtoSave : orderDetailDtoList) {
            final Integer productId = orderDetailDtoSave.getProductId();
            final Integer quantity = orderDetailDtoSave.getQuantity();
            final Location shippingLocation = locationService.getProductMostAbundantShippingLocation(productId,
                    quantity);
            final Product product = productService.findProductById(productId).orElseThrow();
            orderDetails.add(new OrderDetail(null, product, shippingLocation, quantity));
        }
        return orderDetails;
    }

    @Override
    public String getStrategy() {
        return "most_abundant";
    }
}
