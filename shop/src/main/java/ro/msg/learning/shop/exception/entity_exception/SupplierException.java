package ro.msg.learning.shop.exception.entity_exception;

public class SupplierException extends RuntimeException {
    public SupplierException() {
        super();
    }

    public SupplierException(String message, Throwable cause) {
        super(message, cause);
    }

    public SupplierException(String message) {
        super(message);
    }

    public SupplierException(Throwable cause) {
        super(cause);
    }
}
