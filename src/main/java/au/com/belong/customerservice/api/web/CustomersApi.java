package au.com.belong.customerservice.api.web;

import au.com.belong.customerservice.api.exception.ResourceNotFoundException;
import au.com.belong.customerservice.api.models.Phonenumber;
import au.com.belong.customerservice.api.models.ServiceError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.Min;
import java.util.List;

@Validated
@Tag(name = "Customers", description = "Everything about your Customers")
public interface CustomersApi {

    /**
     * GET /customers/{customerId}/phonenumbers : Find phonenumbers by customerId.
     * Get []
     *
     * @param customerId unique identifier of customer to return phonenumbers (required)
     * @return successful operation (status code 200)
     * or Invalid Customer Id (status code 400)
     */
    @Operation(
            operationId = "getPhonenumbersByCustomerId",
            summary = "get phonenumbers by customerId",
            tags = {"Customers"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema =
                            @Schema(implementation =
                                    Phonenumber.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid customerId supplied", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    ServiceError.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "customer not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    ServiceError.class))
                    })})
    @GetMapping(value = "/customers/{customerId}/phonenumbers", produces = {
            "application/json"})
    List<Phonenumber> findPhonenumbersByCustomerId(@Min(1L) @Parameter(name = "customerId", description = "unique "
            + "identifier of customer to return phonenumbers", required = true)
                                                   @PathVariable("customerId") Long customerId)
            throws ResourceNotFoundException;
}
