package ro.msg.learning.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ro.msg.learning.shop.configuration.OrderServiceTestConfiguration;
import ro.msg.learning.shop.exception.entity_exception.LocationException;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.repository.*;
import ro.msg.learning.shop.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//you can ignore this one, i'll delete it
@DataJpaTest
@Import(OrderServiceTestConfiguration.class)
class OrderServiceTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    //integration test for service
    @Autowired
    private OrderService orderService;

    private final Supplier supplier = new Supplier("dan");

    private final ProductCategory productCategory1 = new ProductCategory("food", "eatable");

    private final Address address = new Address("romania", "romania", "romania", "romania");

    private final Product product1 = new Product("chips", "salty", new BigDecimal(10), 5d, productCategory1,
            supplier, "image");
    private final Product product2 = new Product("meat", "salty", new BigDecimal("12.4"), 5d, productCategory1,
            supplier, "image");

    private final Location location = new Location("location one", address);
    private final Location location2 = new Location("location two", address);
    private final Location location3 = new Location("location three", address);

    private final Stock stock = new Stock(product1, location, 10);
    private final Stock stock1 = new Stock(product1, location2, 2);
    private final Stock stock2 = new Stock(product1, location3, 12);
    private final Stock stock3 = new Stock(product2, location, 8);
    private final Stock stock4 = new Stock(product2, location2, 3);
    private final Customer customer = new Customer("Ion", "Ion", "ion", "pass", "email@yahoo.com");


    @BeforeEach
    void init() {
        customerRepository.save(customer);

        supplierRepository.save(supplier);

        productCategoryRepository.save(productCategory1);

        productRepository.save(product1);
        productRepository.save(product2);

        locationRepository.save(location);
        locationRepository.save(location2);
        locationRepository.save(location3);

        stockRepository.save(stock);
        stockRepository.save(stock1);
        stockRepository.save(stock2);
        stockRepository.save(stock3);
        stockRepository.save(stock4);
    }

    @Test
    void testSuccess() {
        OrderDetail orderDetail = new OrderDetail(null, product1, null, 2);
        OrderDetail orderDetail1 = new OrderDetail(null, product2, null, 3);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);
        orderDetails.add(orderDetail1);

        Order order = new Order(customer, LocalDateTime.now(), address);

        Order createdOrder = orderService.saveOrder(order, orderDetails);
        assert (createdOrder != null);
        int day = createdOrder.getCreatedAt().getDayOfMonth();
        assertEquals(day, LocalDateTime.now().getDayOfMonth());
    }

    @Test
    void testFailure() {
        OrderDetail orderDetail = new OrderDetail(null, product1, null, 20);
        OrderDetail orderDetail1 = new OrderDetail(null, product2, null, 3);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);
        orderDetails.add(orderDetail1);

        Order order = new Order(customer, LocalDateTime.now(), address);

        try {
            orderService.saveOrder(order, orderDetails);
            fail();
        } catch (LocationException locationException) {
            assertFalse(locationException.getMessage().isEmpty());
        }
    }
}
