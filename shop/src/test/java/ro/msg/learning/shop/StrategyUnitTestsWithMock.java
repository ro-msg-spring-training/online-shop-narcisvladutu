package ro.msg.learning.shop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.configuration.StrategyUnitTestConfiguration;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.OrderDetailRepository;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.LocationService;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.service.strategy_utils.StrategyService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class StrategyUnitTestsWithMock {
    @Mock
    LocationRepository locationRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    LocationService locationService;

    @Mock
    OrderDetailRepository orderDetailRepository;

    @Mock
    StockRepository stockRepository;

    @Mock
    StockService stockService;

    @Mock
    StrategyService strategyService;

    @InjectMocks
    OrderService orderService;

    @Test
    public void test() {
        Order order = new Order(new Customer("dan", "dan", "dana", "pasroa", "adresa"),
                LocalDateTime.now(),
                new Address("romaina", "romaina", "romaina", "romaina"));
        OrderDetail orderDetail = new OrderDetail(null, new Product(),
                new Location("nume", new Address("romaina", "romaina", "romaina", "romaina")), 1);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);
        orderService.saveOrder(order, orderDetailList);
    }
}
