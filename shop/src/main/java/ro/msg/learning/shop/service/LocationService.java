package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.OrderDetailDtoSave;
import ro.msg.learning.shop.exception.entity_exception.LocationException;
import ro.msg.learning.shop.exception.entity_exception.ProductCategoryException;
import ro.msg.learning.shop.exception.entity_exception.ProductException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.model.Product;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private static final String ERROR_MESSAGE = "location not found for the id ";
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
                () -> new LocationException(ERROR_MESSAGE + id)));
    }

    public void deleteLocation(Integer locationId) {
        if (locationRepository.existsById(locationId)) {
            locationRepository.deleteById(locationId);
        } else {
            throw (new ProductCategoryException(ERROR_MESSAGE + locationId));
        }
    }

    public Location getSingleShippingLocation(final List<OrderDetailDtoSave> orderDetailDtoList) {
        final List<List<Location>> validShippingLocationsPerProduct = getAllValidLocationsForProduct(
                orderDetailDtoList);

        return computeSingleShippingLocation(validShippingLocationsPerProduct);
    }


    public Location getProductMostAbundantShippingLocation(final Integer productId, final Integer quantity) {
        validateProductIdExistence(productId);

        final Location shippingLocation = locationRepository.findMostAbundantShippingLocation(productId, quantity);

        validateLocationNotNull(shippingLocation, productId);

        return shippingLocation;
    }

    private List<List<Location>> getAllValidLocationsForProduct(final List<OrderDetailDtoSave> orderDetailDtoSaveList) {
        final List<List<Location>> validShippingLocationsPerProduct = new ArrayList<>();

        orderDetailDtoSaveList.forEach(orderDetailDtoSave -> {
            final Integer productId = orderDetailDtoSave.getProductId();
            final Integer quantity = orderDetailDtoSave.getQuantity();

            validateProductIdExistence(productId);

            final List<Location> shippingLocationsForCurrentProduct = locationRepository.findAllAvailableShippingLocations(productId, quantity);

            validateShippingLocationsForCurrentProductNotEmpty(shippingLocationsForCurrentProduct, productId);

            validShippingLocationsPerProduct.add(shippingLocationsForCurrentProduct);
        });

        return validShippingLocationsPerProduct;
    }

    private Location computeSingleShippingLocation(final List<List<Location>> allValidShippingLocationsPerProduct) {
        List<Location> validCommonLocations = allValidShippingLocationsPerProduct.get(0);

        allValidShippingLocationsPerProduct.forEach(validCommonLocations::retainAll);

        validateCommonLocationExistence(validCommonLocations);

        return validCommonLocations.get(0);
    }

    private void validateProductIdExistence(final Integer productId) {
        final Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new ProductException("product not found for the id " + productId);
        }
    }

    private void validateShippingLocationsForCurrentProductNotEmpty(final List<Location> validShippingLocationsForCurrentProduct, final Integer productId) {
        if (validShippingLocationsForCurrentProduct == null || validShippingLocationsForCurrentProduct.isEmpty()) {
            throw new LocationException(ERROR_MESSAGE + productId);
        }
    }

    private void validateLocationNotNull(final Location shippingLocation, final Integer productId) {
        if (shippingLocation == null) {
            throw new LocationException(ERROR_MESSAGE + productId);
        }
    }

    private void validateCommonLocationExistence(final List<Location> suitableShippingLocations) {
        if (suitableShippingLocations == null || suitableShippingLocations.isEmpty()) {
            throw new LocationException("common location not found");
        }
    }
}