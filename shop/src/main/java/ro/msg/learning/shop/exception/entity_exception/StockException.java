package ro.msg.learning.shop.exception.entity_exception;

public class StockException  extends RuntimeException {
    public StockException() {
        super();
    }

    public StockException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockException(String message) {
        super(message);
    }

    public StockException(Throwable cause) {
        super(cause);
    }
}