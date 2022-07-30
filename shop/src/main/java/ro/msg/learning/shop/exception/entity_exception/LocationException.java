package ro.msg.learning.shop.exception.entity_exception;

public class LocationException extends RuntimeException {
    public LocationException() {
        super();
    }

    public LocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocationException(String message) {
        super(message);
    }

    public LocationException(Throwable cause) {
        super(cause);
    }
}
