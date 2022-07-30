package ro.msg.learning.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.LocationDto;
import ro.msg.learning.shop.model.Address;
import ro.msg.learning.shop.model.Location;

@Component
@RequiredArgsConstructor
public class LocationMapper {
    public LocationDto toDto(Location location) {
        Integer id = location.getId();
        String name = location.getName();
        Address address = location.getAddress();
        return new LocationDto(id, name, address.getCountry(), address.getCity(), address.getCounty(), address.getStreetAddress());
    }

    public Location toLocation(LocationDto locationDto) {
        return new Location(locationDto.getName(), new Address(locationDto.getAddressCountry(), locationDto.getAddressCity(),
                locationDto.getAddressCounty(), locationDto.getAddressStreetAddress()));
    }
}
