package ro.msg.learning.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.dto.StockDtoSave;
import ro.msg.learning.shop.model.*;

@Component
@RequiredArgsConstructor
public class StockMapper {
    public StockDto toDto(Stock stock) {
        Integer id = stock.getId();
        String productName = stock.getProduct().getName();
        String locationName = stock.getLocation().getName();
        Integer quantity = stock.getQuantity();
        return new StockDto(id, productName, locationName, quantity);
    }

    public Stock toStock(StockDtoSave stockDtoSave, Product product, Location location) {
        return new Stock(product, location, stockDtoSave.getQuantity());
    }
}
