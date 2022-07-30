package ro.msg.learning.shop.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.LocationDto;
import ro.msg.learning.shop.mapper.LocationMapper;
import ro.msg.learning.shop.service.LocationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @PostMapping("/locations")
    public void saveLocation(@RequestBody LocationDto locationDto) {
        this.locationService.saveLocation(locationMapper.toLocation(locationDto));
    }

    @GetMapping("/locations")
    public List<LocationDto> findAllLocations() {
        return this.locationService.findAllLocations().stream().map(locationMapper::toDto).toList();
    }

    @DeleteMapping("/locations/{id}")
    public void deleteLocation(@PathVariable Integer id) {
        locationService.deleteLocation(id);
    }

    @GetMapping("/locations/{id}")
    public LocationDto findLocationById(@PathVariable Integer id) {
        return locationService.findLocationById(id).map(locationMapper::toDto).orElseThrow();
    }
}
