package ro.msg.learning.shop.service.test_utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.repository.*;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RawDataService {
    private final LocationRepository locationRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;

    public void populate() {
        final Supplier supplier = new Supplier("dan");

        final ProductCategory productCategory1 = new ProductCategory("food", "eatable");
        final ProductCategory productCategory2 = new ProductCategory("drink", "drinkable");

        final Address address = new Address("romania", "bucharest", "bucharest", "romania");

        final Product product1 = new Product("chips", "salty", new BigDecimal(10), 5d, productCategory1,
                supplier, "image1");
        final Product product2 = new Product("meat", "protein", new BigDecimal("12.4"), 5d, productCategory1,
                supplier, "image2");
        final Product product3 = new Product("soup", "hot", new BigDecimal(6), 5d, productCategory1,
                supplier, "image3");
        final Product product4 = new Product("cake", "sweet", new BigDecimal(20), 5d, productCategory1,
                supplier, "image4");
        final Product product5 = new Product("coca-cola", "freeze", new BigDecimal(11), 5d, productCategory2,
                supplier, "image5");
        final Product product6 = new Product("water", "CO2", new BigDecimal(13), 5d, productCategory2,
                supplier, "image6");
        final Product product7 = new Product("sprite", "lemon", new BigDecimal(9), 5d, productCategory2,
                supplier, "image7");

        final Location location = new Location("location one", address);
        final Location location2 = new Location("location two", address);
        final Location location3 = new Location("location three", address);

        final Stock stock = new Stock(product1, location, 10);
        final Stock stock1 = new Stock(product1, location2, 2);
        final Stock stock2 = new Stock(product1, location3, 12);
        final Stock stock3 = new Stock(product2, location, 8);
        final Stock stock4 = new Stock(product2, location2, 3);
        final Stock stock5 = new Stock(product3, location3, 9);
        final Stock stock6 = new Stock(product4, location, 7);
        final Stock stock7 = new Stock(product5, location, 5);
        final Stock stock8 = new Stock(product6, location, 11);
        final Stock stock9 = new Stock(product6, location2, 4);
        final Stock stock10 = new Stock(product6, location3, 7);

        final Customer customer = new Customer("Ion", "Ion", "ion", "pass", "email@yahoo.com");

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

        customerRepository.save(customer);
    }

    public void clear() {
        orderDetailRepository.deleteAll();
        orderRepository.deleteAll();
        stockRepository.deleteAll();
        locationRepository.deleteAll();
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
        supplierRepository.deleteAll();
        customerRepository.deleteAll();
    }
}
