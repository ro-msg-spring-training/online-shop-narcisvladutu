package ro.msg.learning.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.exception.entity_exception.ProductCategoryException;
import ro.msg.learning.shop.exception.entity_exception.StockException;
import ro.msg.learning.shop.model.Stock;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {
    private static final String ERROR_MESSAGE = "stock not found for the id ";
    private final StockRepository stockRepository;

    public void saveStock(Stock stock) {
        stockRepository.save(stock);
    }

    public List<Stock> findAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> findStockById(final Integer id) {
        return Optional.ofNullable(stockRepository.findById(id).orElseThrow(
                () -> new StockException(ERROR_MESSAGE + id)));
    }

    public Optional<Stock> findStockByLocationAndProduct(final Integer locationId, final Integer productId) {
        return Optional.ofNullable(stockRepository.findByLocationAndProduct(locationId, productId));
    }

    public void deleteStock(Integer productId) {
        if (stockRepository.existsById(productId)) {
            stockRepository.deleteById(productId);
        } else {
            throw (new ProductCategoryException(ERROR_MESSAGE + productId));
        }
    }

    public void updateStock(final Stock stock) {
        if (stockRepository.existsById(stock.getId())) {
            stockRepository.save(stock);
        } else {
            throw (new StockException(ERROR_MESSAGE + stock.getId()));
        }
    }

    public void updateStock(final Integer locationId, final Integer productId, final Integer quantity) {
        Optional<Stock> stock = findStockByLocationAndProduct(locationId, productId);
        if (stock.isPresent()) {
            updateStockQuantity(stock.get(), quantity);
        } else {
            throw new StockException(ERROR_MESSAGE);
        }
    }

    public void updateStockQuantity(final Stock stock, final Integer takenQuantity) {
        if (stockRepository.existsById(stock.getId())) {
            stock.setQuantity(stock.getQuantity() - takenQuantity);
            stockRepository.save(stock);
        } else {
            throw (new StockException(ERROR_MESSAGE + stock.getId()));
        }
    }
}
