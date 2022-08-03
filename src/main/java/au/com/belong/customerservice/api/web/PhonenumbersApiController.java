package au.com.belong.customerservice.api.web;


import au.com.belong.customerservice.api.exception.ResourceNotFoundException;
import au.com.belong.customerservice.api.models.Phonenumber;
import au.com.belong.customerservice.api.service.PhonenumberService;
import brave.baggage.BaggageField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PhonenumbersApiController implements PhonenumbersApi {
    private final BaggageField phonenumberField;
    private final PhonenumberService phonenumberService;

    @Override
    public Phonenumber activatePhonenumber(String phonenumberId, Phonenumber phonenumber)
            throws ResourceNotFoundException {
        phonenumberField.updateValue(phonenumberId);
        log.info("operation=activatePhonenumber Request Received");
        return phonenumberService.activatePhonenumber(phonenumberId, phonenumber);
    }

    @Override
    public List<Phonenumber> getPhonenumbers() {
        return phonenumberService.getPhonenumbers();
    }

    @Override
    public Phonenumber getPhonenumberById(String phonenumberId) throws ResourceNotFoundException {
        phonenumberField.updateValue(phonenumberId);
        return phonenumberService.getPhonenumberById(phonenumberId);
    }
}
