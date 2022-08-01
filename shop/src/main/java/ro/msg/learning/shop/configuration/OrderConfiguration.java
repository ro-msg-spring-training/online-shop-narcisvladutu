package ro.msg.learning.shop.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ro.msg.learning.shop.service.strategy_utils.StrategyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class OrderConfiguration {
    @Value("${strategy:single_location}")
    private String strategyName;

    private final Map<String, StrategyService> availableOrderDetailServices;

    public OrderConfiguration(final List<StrategyService> strategyImplementations) {
        availableOrderDetailServices = getStrategyImplementationsMap(strategyImplementations);
    }

    private Map<String, StrategyService> getStrategyImplementationsMap(
            final List<StrategyService> strategyImplementations) {
        final Map<String, StrategyService> orderDetailServices = new HashMap<>();

        for (final StrategyService strategy : strategyImplementations) {
            orderDetailServices.put(strategy.getStrategy(), strategy);
        }

        return orderDetailServices;
    }

    @Bean
    @Primary
    public StrategyService strategy() {
        return availableOrderDetailServices.get(strategyName);
    }
}
