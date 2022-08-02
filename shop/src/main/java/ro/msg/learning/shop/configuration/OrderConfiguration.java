package ro.msg.learning.shop.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.service.strategy_utils.MostAbundantStrategy;
import ro.msg.learning.shop.service.strategy_utils.SingleLocationStrategy;
import ro.msg.learning.shop.service.strategy_utils.StrategyService;

@Configuration
@RequiredArgsConstructor
public class OrderConfiguration {
    @Value("${strategy:most_abundant}")
    private String strategyName;

    private final LocationService locationService;
    private final OrderDetailRepository orderDetailRepository;
    private final StockService stockService;

    @Bean
    @Primary
    public StrategyService strategyService() {
        if (strategyName.equals("single_location")) {
            return new SingleLocationStrategy(locationService, orderDetailRepository, stockService);
        } else {
            return new MostAbundantStrategy(locationService, orderDetailRepository, stockService);
        }
    }
}
