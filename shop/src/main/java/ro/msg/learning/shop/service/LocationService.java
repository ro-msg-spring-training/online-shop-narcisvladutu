package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.entity_exception.LocationException;
import ro.msg.learning.shop.exception.entity_exception.ProductCategoryException;
import ro.msg.learning.shop.model.Location;
import ro.msg.learning.shop.repository.LocationRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public void saveLocation(Location location) {
        locationRepository.save(location);
    }

    public List<Location> findAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Location> findLocationById(final Integer id) {
        return Optional.ofNullable(locationRepository.findById(id).orElseThrow(
                () -> new LocationException("location not found for the id " + id)));
    }

    public void deleteLocation(Integer locationId) {
        if (locationRepository.existsById(locationId)) {
            locationRepository.deleteById(locationId);
        } else {
            throw (new ProductCategoryException("location not found for the id " + locationId));
        }
    }
}