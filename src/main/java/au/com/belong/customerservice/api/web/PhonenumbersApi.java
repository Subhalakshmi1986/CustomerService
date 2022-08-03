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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@Validated
@Tag(name = "Phonenumbers", description = "Access to Customer phonenumber")
public interface PhonenumbersApi {
    /**
     * PATCH /phonenumbers/{phonenumberId} : activate or deactivate a phonenumber
     *
     * @param phonenumberId phonenumber of the customer to update (required)
     * @param phonenumber   (optional)
     * @return successful operation (status code 200)
     * or Invalid phonenumber supplied (status code 400)
     * or phonenumber not found (status code 404)
     */
    @Operation(
            operationId = "activatesPhonenumber",
            summary = "activate or deactivate a phonenumber",
            tags = {"Phonenumbers"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    Phonenumber.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid phonenumber supplied", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    ServiceError.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "phonenumber not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    ServiceError.class))
                    })
            }
    )
    @PatchMapping(
            value = "/phonenumbers/{phonenumberId}",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    Phonenumber activatePhonenumber(
            @Pattern(regexp = "^\\d{9}$") @Parameter(name = "phonenumberId", description = "phonenumber of the "
                    + "customer to update", required = true) @PathVariable("phonenumberId") String phonenumberId,
            @Parameter(name = "Phonenumber", description = "Phonenumber") @Valid @RequestBody(required = false)
                    Phonenumber phonenumber
    ) throws ResourceNotFoundException;

    /**
     * GET /phonenumbers : Get all phonenumbers
     * Get all phonenumbers
     *
     * @return successful operation (status code 200)
     */
    @Operation(
            operationId = "getPhonenumbers",
            summary = "Get all phonenumbers",
            tags = {"Phonenumbers"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema =
                            @Schema(implementation =
                                    Phonenumber.class)))
                    })
            }
    )
    @GetMapping(
            value = "/phonenumbers",
            produces = {"application/json"}
    )
    List<Phonenumber> getPhonenumbers();


    /**
     * GET /phonenumbers/{phonenumberId} : Find a single phonenumber
     * Returns a single phonenumber
     *
     * @param phonenumberId phonenumberId of phonenumber to return (required)
     * @return successful operation (status code 200)
     * or Invalid phonenumber (status code 400)
     * or phonenumber not found (status code 404)
     */
    @Operation(
            operationId = "getPhonenumberById",
            summary = "get a single phonenumber by id",
            tags = {"Phonenumbers"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    Phonenumber.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Invalid phonenumber", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    ServiceError.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "phonenumber not found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation =
                                    ServiceError.class))
                    })
            }
    )
    @GetMapping(
            value = "/phonenumbers/{phonenumberId}",
            produces = {"application/json"}
    )
    Phonenumber getPhonenumberById(
            @Pattern(regexp = "^\\d{9}$") @Parameter(name = "phonenumberId", description = "phonenumberId of "
                    + "phonenumber to return", required = true) @PathVariable("phonenumberId") String phonenumberId
    ) throws ResourceNotFoundException;
}
