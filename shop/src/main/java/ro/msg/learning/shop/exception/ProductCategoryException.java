package ro.msg.learning.shop.exception;

public class ProductCategoryException extends RuntimeException {
    public ProductCategoryException() {
        super();
    }

    public ProductCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductCategoryException(String message) {
        super(message);
    }

    public ProductCategoryException(Throwable cause) {
        super(cause);
    }
}
