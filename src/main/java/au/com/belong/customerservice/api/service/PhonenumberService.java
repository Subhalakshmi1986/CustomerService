package au.com.belong.customerservice.api.service;

import au.com.belong.customerservice.api.data.repository.PhonenumberRepository;
import au.com.belong.customerservice.api.exception.ResourceNotFoundException;
import au.com.belong.customerservice.api.mapper.PhonenumberMapper;
import au.com.belong.customerservice.api.models.Phonenumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhonenumberService {
    private final PhonenumberMapper phonenumberMapper;

    private final PhonenumberRepository phonenumberRepository;

    public final Phonenumber activatePhonenumber(String phonenumberId, Phonenumber phonenumber)
            throws ResourceNotFoundException {
        au.com.belong.customerservice.api.data.dao.Phonenumber existingPhonenumber =
                phonenumberRepository.findByPhonenumberId(phonenumberId);
        if (existingPhonenumber == null) {
            log.info("operation=activatePhonenumber Phonenumber not found");
            throw new ResourceNotFoundException(String.format("phonenumber not found: %s", phonenumberId));
        } else {
            existingPhonenumber.setStatus(phonenumber.getStatus().getValue());
            au.com.belong.customerservice.api.data.dao.Phonenumber updated =
                    phonenumberRepository.save(existingPhonenumber);
            log.info("operation=activatePhonenumber Phonenumber activated");
            return phonenumberMapper.mapPhonenumber(updated);
        }
    }

    public final List<Phonenumber> getPhonenumbers() {
        log.info("operation=getPhonenumbers Get Phonenumbers");
        return phonenumberRepository
                .findAll()
                .stream()
                .map(phonenumberMapper::mapPhonenumber)
                .collect(Collectors.toList());
    }

    public final Phonenumber getPhonenumberById(String phonenumberId) throws ResourceNotFoundException {
        Optional<au.com.belong.customerservice.api.data.dao.Phonenumber> optional =
                phonenumberRepository.findById(phonenumberId);
        if (optional.isPresent()) {
            log.info("operation=getPhonenumberById phonenumber retrieved");
            return phonenumberMapper.mapPhonenumber(optional.get());
        } else {
            throw new ResourceNotFoundException(String.format("phonenumber not found: %s", phonenumberId));
        }

    }
}
