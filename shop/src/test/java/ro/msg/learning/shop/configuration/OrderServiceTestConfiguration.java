package ro.msg.learning.shop.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import ro.msg.learning.shop.repository.CustomerRepository;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.service.strategy_utils.MostAbundantStrategy;

@TestConfiguration
@RequiredArgsConstructor
@Import({LocationService.class, StockService.class})
public class OrderServiceTestConfiguration {
    private final LocationService locationService;
    private final OrderDetailRepository orderDetailRepository;
    private final StockService stockService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Bean
    public OrderService orderService() {
        return new OrderService(new MostAbundantStrategy(locationService, orderDetailRepository), orderRepository, orderDetailRepository, customerRepository, productRepository, stockService);
    }
}
