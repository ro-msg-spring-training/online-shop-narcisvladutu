package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.entity_exception.LocationException;
import ro.msg.learning.shop.exception.entity_exception.ProductCategoryException;
import ro.msg.learning.shop.exception.entity_exception.ProductException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.OrderDetail;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private static final String ERROR_MESSAGE_LOCATION = "location not found for the id ";
    private static final String ERROR_MESSAGE_PRODUCT = "product not found for the id ";
    private static final String ERROR_MESSAGE_SUITABLE_LOCATION = "common location not found";
    private final LocationRepository locationRepository;

    private final ProductRepository productRepository;

    public void saveLocation(Location location) {
        locationRepository.save(location);
    }

    public List<Location> findAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Location> findLocationById(final Integer id) {
        return Optional.ofNullable(locationRepository.findById(id).orElseThrow(
                () -> new LocationException(ERROR_MESSAGE_LOCATION + id)));
    }

    public void deleteLocation(Integer locationId) {
        if (locationRepository.existsById(locationId)) {
            locationRepository.deleteById(locationId);
        } else {
            throw (new ProductCategoryException(ERROR_MESSAGE_LOCATION + locationId));
        }
    }

    public Location getSingleShippingLocation(final List<OrderDetail> orderDetails) {
        final List<List<Location>> validShippingLocationsPerProduct = getAllValidLocationsForProduct(
                orderDetails);

        return findCommonShippingLocation(validShippingLocationsPerProduct);
    }


    public Location getProductMostAbundantShippingLocation(final Integer productId, final Integer quantity) {
        validateProduct(productId);

        final Location shippingLocation = locationRepository.findMostAbundantShippingLocation(productId, quantity);

        if (shippingLocation == null) {
            throw new LocationException(ERROR_MESSAGE_LOCATION + productId);
        }

        return shippingLocation;
    }

    private List<List<Location>> getAllValidLocationsForProduct(final List<OrderDetail> orderDetails) {
        final List<List<Location>> validShippingLocationsPerProduct = new ArrayList<>();

        orderDetails.forEach(orderDetail -> {
            final Integer productId = orderDetail.getProduct().getId();
            final Integer quantity = orderDetail.getQuantity();

            validateProduct(productId);

            final List<Location> shippingLocationsForCurrentProduct = locationRepository.findAllAvailableShippingLocations(productId, quantity);

            if (shippingLocationsForCurrentProduct.isEmpty()) {
                throw new LocationException(ERROR_MESSAGE_LOCATION + productId);
            }

            validShippingLocationsPerProduct.add(shippingLocationsForCurrentProduct);
        });

        return validShippingLocationsPerProduct;
    }

    private Location findCommonShippingLocation(final List<List<Location>> allValidShippingLocationsPerProduct) {
        List<Location> validCommonLocations = allValidShippingLocationsPerProduct.get(0);

        allValidShippingLocationsPerProduct.forEach(validCommonLocations::retainAll);

        if (validCommonLocations.isEmpty()) {
            throw new LocationException(ERROR_MESSAGE_SUITABLE_LOCATION);
        }

        return validCommonLocations.get(0);
    }

    private void validateProduct(final Integer productId) {
        final Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductException(ERROR_MESSAGE_PRODUCT + productId);
        }
    }
}