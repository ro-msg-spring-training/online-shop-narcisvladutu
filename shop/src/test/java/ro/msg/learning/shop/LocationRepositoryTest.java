package ro.msg.learning.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ro.msg.learning.shop.configuration.LocationRepositoryTestConfiguration;
import ro.msg.learning.shop.exception.entity_exception.LocationException;
import ro.msg.learning.shop.exception.entity_exception.ProductException;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.repository.*;
import ro.msg.learning.shop.service.strategy_utils.MostAbundantStrategy;
import ro.msg.learning.shop.service.strategy_utils.SingleLocationStrategy;
import ro.msg.learning.shop.service.strategy_utils.StrategyService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@Import(LocationRepositoryTestConfiguration.class)
class LocationRepositoryTest {
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

    @Autowired
    private StrategyService mostAbundantStrategy;

    @Autowired
    @Qualifier("singleLocationStrategy")
    private StrategyService singleLocationStrategy;

    private final Supplier supplier = new Supplier("dan");

    private final ProductCategory productCategory1 = new ProductCategory("food", "eatable");
    private final ProductCategory productCategory2 = new ProductCategory("drink", "drinkable");

    private final Address address = new Address("romania", "romania", "romania", "romania");

    private final Product product1 = new Product("chips", "salty", new BigDecimal(10), 5d, productCategory1,
            supplier, "image");
    private final Product product2 = new Product("meat", "salty", new BigDecimal("12.4"), 5d, productCategory1,
            supplier, "image");
    private final Product product3 = new Product("soup", "salty", new BigDecimal(6), 5d, productCategory1,
            supplier, "image");
    private final Product product4 = new Product("cake", "salty", new BigDecimal(20), 5d, productCategory1,
            supplier, "image");
    private final Product product5 = new Product("coca-cola", "salty", new BigDecimal(11), 5d, productCategory2,
            supplier, "image");
    private final Product product6 = new Product("water", "salty", new BigDecimal(13), 5d, productCategory2,
            supplier, "image");
    private final Product product7 = new Product("sprite", "salty", new BigDecimal(9), 5d, productCategory2,
            supplier, "image");

    private final Location location = new Location("location one", address);
    private final Location location2 = new Location("location two", address);
    private final Location location3 = new Location("location three", address);

    private final Stock stock = new Stock(product1, location, 10);
    private final Stock stock1 = new Stock(product1, location2, 2);
    private final Stock stock2 = new Stock(product1, location3, 12);
    private final Stock stock3 = new Stock(product2, location, 8);
    private final Stock stock4 = new Stock(product2, location2, 3);
    private final Stock stock5 = new Stock(product3, location3, 9);
    private final Stock stock6 = new Stock(product4, location, 7);
    private final Stock stock7 = new Stock(product5, location, 5);
    private final Stock stock8 = new Stock(product6, location, 11);
    private final Stock stock9 = new Stock(product6, location2, 4);
    private final Stock stock10 = new Stock(product6, location3, 7);

    @BeforeEach
    void save() {
        supplierRepository.save(supplier);

        productCategoryRepository.save(productCategory1);
        productCategoryRepository.save(productCategory2);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);
        productRepository.save(product6);
        productRepository.save(product7);

        locationRepository.save(location);
        locationRepository.save(location2);
        locationRepository.save(location3);

        stockRepository.save(stock);
        stockRepository.save(stock1);
        stockRepository.save(stock2);
        stockRepository.save(stock3);
        stockRepository.save(stock4);
        stockRepository.save(stock5);
        stockRepository.save(stock6);
        stockRepository.save(stock7);
        stockRepository.save(stock8);
        stockRepository.save(stock9);
        stockRepository.save(stock10);
    }

    @Test
    void shouldFindAll() {
        List<Location> locations = locationRepository.findAll();
        List<Product> products = productRepository.findAll();
        List<Stock> stocks = stockRepository.findAll();
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        List<Supplier> suppliers = supplierRepository.findAll();
        assertEquals(3, locations.size());
        assertEquals(7, products.size());
        assertEquals(11, stocks.size());
        assertEquals(2, productCategories.size());
        assertEquals(1, suppliers.size());
    }

    @Test
    void shouldFindMostAbundantShippingLocation() {
        assertEquals(mostAbundantStrategy.getClass(), MostAbundantStrategy.class);

        OrderDetail orderDetail = new OrderDetail(null, product1, null, 4);
        OrderDetail orderDetail1 = new OrderDetail(null, product2, null, 3);
        OrderDetail orderDetail2 = new OrderDetail(null, product3, null, 2);
        OrderDetail orderDetail3 = new OrderDetail(null, product6, null, 8);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);
        orderDetails.add(orderDetail1);
        orderDetails.add(orderDetail2);
        orderDetails.add(orderDetail3);

        List<OrderDetail> orderDetailsWithLocation = mostAbundantStrategy.findOrderDetailsLocation(orderDetails);
        assertEquals(orderDetailsWithLocation.get(0).getLocation(), location3);
        assertEquals(orderDetailsWithLocation.get(1).getLocation(), location);
        assertEquals(orderDetailsWithLocation.get(2).getLocation(), location3);
        assertEquals(orderDetailsWithLocation.get(3).getLocation(), location);
    }

    @Test
    void shouldFindMostAbundantShippingLocationUnavailable() {
        OrderDetail orderDetail = new OrderDetail(null, product1, null, 20);
        OrderDetail orderDetail1 = new OrderDetail(null, product2, null, 3);
        OrderDetail orderDetail2 = new OrderDetail(null, product3, null, 2);
        OrderDetail orderDetail3 = new OrderDetail(null, product6, null, 8);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);
        orderDetails.add(orderDetail1);
        orderDetails.add(orderDetail2);
        orderDetails.add(orderDetail3);

        try {
            mostAbundantStrategy.findOrderDetailsLocation(orderDetails);
            fail();
        } catch (LocationException locationException) {
            assert (!locationException.getMessage().isEmpty());
        }
    }

    @Test
    void shouldFindCommonShippingLocation() {
        assertEquals(singleLocationStrategy.getClass(), SingleLocationStrategy.class);
        OrderDetail orderDetail = new OrderDetail(null, product1, null, 2);
        OrderDetail orderDetail1 = new OrderDetail(null, product2, null, 2);
        OrderDetail orderDetail3 = new OrderDetail(null, product6, null, 2);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);
        orderDetails.add(orderDetail1);
        orderDetails.add(orderDetail3);

        List<OrderDetail> orderDetailsWithLocation = singleLocationStrategy.findOrderDetailsLocation(orderDetails);
        assertEquals(orderDetailsWithLocation.get(0).getLocation(), location);
        assertEquals(orderDetailsWithLocation.get(1).getLocation(), location);
        assertEquals(orderDetailsWithLocation.get(2).getLocation(), location);
    }

    @Test
    void shouldFindCommonShippingLocationUnavailable() {
        OrderDetail orderDetail = new OrderDetail(null, product1, null, 2);
        OrderDetail orderDetail1 = new OrderDetail(null, product2, null, 2);
        OrderDetail orderDetail3 = new OrderDetail(null, product3, null, 2);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);
        orderDetails.add(orderDetail1);
        orderDetails.add(orderDetail3);

        try {
            singleLocationStrategy.findOrderDetailsLocation(orderDetails);
            fail();
        } catch (LocationException locationException) {
            assert (!locationException.getMessage().isEmpty());
        }
    }

    @Test
    void shouldFindMostCommonShippingLocationUnavailableProduct() {
        Product product = new Product("name", "description", new BigDecimal(2), 4d, productCategory1, supplier, "image");
        product.setId(20000);
        OrderDetail orderDetail = new OrderDetail(null, product, null, 2);
        OrderDetail orderDetail1 = new OrderDetail(null, product2, null, 2);
        OrderDetail orderDetail3 = new OrderDetail(null, product3, null, 2);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(orderDetail);
        orderDetails.add(orderDetail1);
        orderDetails.add(orderDetail3);

        try {
            singleLocationStrategy.findOrderDetailsLocation(orderDetails);
            fail();
        } catch (ProductException productException) {
            assert (!productException.getMessage().isEmpty());
        }
    }
}