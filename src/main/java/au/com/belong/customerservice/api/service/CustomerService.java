package au.com.belong.customerservice.api.service;

import au.com.belong.customerservice.api.data.repository.CustomerRepository;
import au.com.belong.customerservice.api.exception.ResourceNotFoundException;
import au.com.belong.customerservice.api.mapper.CustomerMapper;
import au.com.belong.customerservice.api.models.Customer;
import au.com.belong.customerservice.api.models.Phonenumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public final List<Phonenumber> getPhonenumbersByCustomerId(Long customerId) throws ResourceNotFoundException {
        Optional<au.com.belong.customerservice.api.data.dao.Customer> optionalCustomerDao = customerRepository
                .findById(customerId);
        if (optionalCustomerDao.isEmpty()) {
            throw new ResourceNotFoundException(String.format("customer not found: %s", customerId));
        } else {
            Customer customer = customerMapper.mapCustomer(optionalCustomerDao.get());
            log.info("operation=getPhonenumbersByCustomerId phonenumbers retrieved");
            return customer.getPhonenumbers();
        }
    }


}
