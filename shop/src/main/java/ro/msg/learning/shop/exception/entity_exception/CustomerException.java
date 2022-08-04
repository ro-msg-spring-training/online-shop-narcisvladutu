package ro.msg.learning.shop.exception.entity_exception;

import java.util.function.Supplier;

public class CustomerException extends RuntimeException {
    public CustomerException() {
        super();
    }

    public CustomerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerException(String message) {
        super(message);
    }

    public CustomerException(Throwable cause) {
        super(cause);
    }
}
