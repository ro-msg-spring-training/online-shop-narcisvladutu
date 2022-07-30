package ro.msg.learning.shop.contoller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.msg.learning.shop.exception.ApiError;
import ro.msg.learning.shop.exception.entity_exception.ProductCategoryException;
import ro.msg.learning.shop.exception.entity_exception.ProductException;
import ro.msg.learning.shop.exception.entity_exception.SupplierException;

import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ProductCategoryException.class, SupplierException.class, ProductException.class})
    public ResponseEntity<Object> handleEntityNotFound(RuntimeException exception) {
        return new ResponseEntity<>(new ApiError(exception.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now()),
                HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now()),
                HttpStatus.NOT_FOUND);
    }
}
