package ro.msg.learning.shop.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.StockDto;
import ro.msg.learning.shop.dto.StockDtoSave;
import ro.msg.learning.shop.mapper.StockMapper;
import ro.msg.learning.shop.model.*;
import ro.msg.learning.shop.service.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;
    private final ProductService productService;
    private final LocationService locationService;
    private final StockMapper stockMapper;

    @GetMapping("/stocks")
    public List<StockDto> findAllStocks() {
        final List<Stock> stocks = stockService.findAllStocks();
        return stocks.stream().map(stockMapper::toDto).toList();
    }

    @PostMapping("/stocks")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProduct(@RequestBody StockDtoSave stockDtoSave) {
        Optional<Product> productOptional = productService.findProductById(stockDtoSave.getProductId());
        Optional<Location> locationOptional = locationService.findLocationById(stockDtoSave.getLocationId());
        if (productOptional.isPresent() && locationOptional.isPresent()) {
            Product product = productOptional.get();
            Location location = locationOptional.get();
            stockService.saveStock(stockMapper.toStock(stockDtoSave, product, location));
        }
    }

    @DeleteMapping("/stocks/{id}")
    public void deleteStock(@PathVariable Integer id) {
        stockService.deleteStock(id);
    }

    @GetMapping("/stocks/{id}")
    public StockDto findStockById(@PathVariable Integer id) {
        return stockService.findStockById(id).map(stockMapper::toDto).orElseThrow();
    }

    @PutMapping(value = "/stocks/{id}")
    public void updateStock(@PathVariable("id") final Integer id, @RequestBody final StockDtoSave stockDtoSave) {
        Optional<Product> productOptional = productService.findProductById(stockDtoSave.getProductId());
        Optional<Location> locationOptional = locationService.findLocationById(stockDtoSave.getLocationId());
        if (productOptional.isPresent() && locationOptional.isPresent()) {
            Product product = productOptional.get();
            Location location = locationOptional.get();
            final Stock stock = stockMapper.toStock(stockDtoSave, product, location);
            stock.setId(id);
            stockService.updateStock(stock);
        }
    }
}
