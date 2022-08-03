package ro.msg.learning.shop.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.service.strategy_utils.MostAbundantStrategy;
import ro.msg.learning.shop.service.strategy_utils.SingleLocationStrategy;
import ro.msg.learning.shop.service.strategy_utils.StrategyService;

@TestConfiguration
@RequiredArgsConstructor
@Import({LocationService.class, StockService.class})
public class LocationRepositoryTestConfiguration {
    private final LocationService locationService;
    private final OrderDetailRepository orderDetailRepository;
    private final StockService stockService;

    @Bean
    @Primary
    public StrategyService strategyService() {
        return new MostAbundantStrategy(locationService, orderDetailRepository, stockService);
    }

    @Bean(name = "singleLocationStrategy")
    public StrategyService strategyService2() {
        return new SingleLocationStrategy(locationService, orderDetailRepository, stockService);
    }
}