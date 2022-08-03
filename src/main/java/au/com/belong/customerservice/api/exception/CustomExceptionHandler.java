package au.com.belong.customerservice.api.exception;

import au.com.belong.customerservice.api.models.ServiceError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ServiceError handleAllExceptions(Exception ex) {
        logException(ex);
        return new ServiceError().message(ex.getMessage()).error("GEN_001").detail("Please reach out to "
                + "belong-support@belong.com.au.");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ServiceError handleConstraintViolationException(ConstraintViolationException ex) {
        logException(ex);
        return new ServiceError().message(ex.getMessage()).error("VAL_001").detail("Please send a valid request.");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ServiceError handleResourceNotFoundException(ResourceNotFoundException ex) {
        logException(ex);
        return new ServiceError().message(ex.getMessage()).error("RES_001").detail("Resource not found.Please "
                + "query valid resource.");
    }

    private void logException(Exception ex) {
        log.error(String.format("%s: %s", ex.getClass(), ex.getMessage()));
    }

}
