package ro.msg.learning.shop;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.repository.*;
import ro.msg.learning.shop.service.LocationService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


//mock
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ShopApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestPropertySource(
        locations = "classpath:application.properties")
public class StrategyUnitWithMockTests {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private OrderDetailRepository orderDetailRepository;
    @InjectMocks
    private LocationService locationService;


    @Before
    public void init() {
        Supplier supplier = new Supplier("mihai");
        supplier.setId(1);

        ProductCategory productCategory = new ProductCategory("water", "wet");
        productCategory.setId(1);

        Product product = new Product("aqua", "natural", new BigDecimal(12), 1d,
                productCategory, supplier, "http");
        product.setId(1);

        Location location1 = new Location("Location 1",
                new Address("Romania", "Romania", "Romania", "Romania"));
        location1.setId(1);
        Location location2 = new Location("Location 2",
                new Address("Romania", "Romania", "Romania", "Romania"));
        location2.setId(1);

        Customer customer = new Customer("name", "name", "name", "name", "name@gmail.com");
        customer.setId(1);

        Stock stock1 = new Stock(product, location1, 15);
        Stock stock2 = new Stock(product, location2, 20);
        stock1.setId(1);
        stock2.setId(2);

        List<Location> locations = new ArrayList<>();
        locations.add(location1);
        locations.add(location2);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(locationRepository.findById(1)).thenReturn(Optional.of(location1));
        when(locationRepository.findById(2)).thenReturn(Optional.of(location2));
        when(locationRepository.findMostAbundantShippingLocation(1, 10)).thenReturn(location2);
        when(locationRepository.findAllAvailableShippingLocations(1, 10)).thenReturn(
                locations);
    }

    @Test
    public void testMostAbundantShippingLocation() {
        Location location = locationService.getProductMostAbundantShippingLocation(1, 10);
        assertEquals("Location 2", location.getName());
    }
}
