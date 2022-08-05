package ro.msg.learning.shop.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.strategy_utils.MostAbundantStrategy;
import ro.msg.learning.shop.service.strategy_utils.SingleLocationStrategy;
import ro.msg.learning.shop.service.strategy_utils.StrategyService;

@Configuration
@RequiredArgsConstructor
public class OrderConfiguration {
    @Value("${strategy}")
    private Strategy strategyName;

    private enum Strategy {
        MOST_ABUNDANT, SINGLE_LOCATION
    }

    private final LocationService locationService;
    private final OrderDetailRepository orderDetailRepository;

    @Bean
    @Primary
    public StrategyService strategyService() {
        return switch (strategyName) {
            case MOST_ABUNDANT -> new MostAbundantStrategy(locationService, orderDetailRepository);
            case SINGLE_LOCATION -> new SingleLocationStrategy(locationService, orderDetailRepository);
        };
    }
}
