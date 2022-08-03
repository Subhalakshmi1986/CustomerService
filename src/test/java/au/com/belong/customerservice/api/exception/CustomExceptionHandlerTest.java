package au.com.belong.customerservice.api.exception;

import au.com.belong.customerservice.api.models.ServiceError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomExceptionHandlerTest {

    @InjectMocks
    private CustomExceptionHandler customExceptionHandler;

    @Test
    void shouldHandleAllExceptions() {
        Exception exception = new Exception("Error ocurred");
        ServiceError expectedError = new ServiceError().error("GEN_001").message(exception.getMessage()).detail(
                "Please reach out to belong-support@belong.com.au.");

        ServiceError error = customExceptionHandler.handleAllExceptions(exception);

        assertThat(error).usingRecursiveComparison().isEqualTo(expectedError);
    }

    @Test
    void shouldHandleConstraintViolationException() {
        Set<? extends ConstraintViolation<?>> violations = new HashSet<>();
        ConstraintViolationException exception = new ConstraintViolationException("constraint violated", violations);
        ServiceError expectedError = new ServiceError().error("VAL_001").message(exception.getMessage()).detail(
                "Please send a valid request.");

        ServiceError error = customExceptionHandler.handleConstraintViolationException(exception);

        assertThat(error).usingRecursiveComparison().isEqualTo(expectedError);
    }

    @Test
    void shouldHandleResourceNotFoundException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("customer not found: 123");
        ServiceError expectedError = new ServiceError().error("RES_001").message(exception.getMessage()).detail(
                "Resource not found.Please query valid resource.");

        ServiceError error = customExceptionHandler.handleResourceNotFoundException(exception);

        assertThat(error).usingRecursiveComparison().isEqualTo(expectedError);
    }

}
