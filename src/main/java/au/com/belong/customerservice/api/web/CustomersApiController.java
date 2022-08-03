package au.com.belong.customerservice.api.web;


import au.com.belong.customerservice.api.exception.ResourceNotFoundException;
import au.com.belong.customerservice.api.models.Phonenumber;
import au.com.belong.customerservice.api.service.CustomerService;
import brave.baggage.BaggageField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomersApiController implements CustomersApi {
    private final BaggageField customerField;
    private final CustomerService customerService;

    @Override
    public List<Phonenumber> findPhonenumbersByCustomerId(Long customerId) throws ResourceNotFoundException {
        customerField.updateValue(String.valueOf(customerId));
        log.info("operation=getPhonenumbersByCustomerId Request received");
        return customerService.getPhonenumbersByCustomerId(customerId);
    }
}
