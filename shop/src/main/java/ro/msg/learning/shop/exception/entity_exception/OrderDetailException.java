package ro.msg.learning.shop.exception.entity_exception;

public class OrderDetailException  extends RuntimeException {
    public OrderDetailException() {
        super();
    }

    public OrderDetailException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderDetailException(String message) {
        super(message);
    }

    public OrderDetailException(Throwable cause) {
        super(cause);
    }
}