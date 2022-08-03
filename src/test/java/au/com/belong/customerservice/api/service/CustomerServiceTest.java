package au.com.belong.customerservice.api.service;

import au.com.belong.customerservice.api.data.dao.Customer;
import au.com.belong.customerservice.api.data.repository.CustomerRepository;
import au.com.belong.customerservice.api.exception.ResourceNotFoundException;
import au.com.belong.customerservice.api.mapper.CustomerMapper;
import au.com.belong.customerservice.api.models.Phonenumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static au.com.belong.customerservice.api.fixture.CustomerFixture.CUSTOMER_ID;
import static au.com.belong.customerservice.api.fixture.CustomerFixture.getCustomer;
import static au.com.belong.customerservice.api.fixture.CustomerFixture.getCustomerDao;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;


    @Test
    void shouldGetPhonenumbersByCustomerId() {
        Customer expectedCustomerDao = getCustomerDao();
        au.com.belong.customerservice.api.models.Customer expectedCustomer = getCustomer();
        given(customerRepository.findById(CUSTOMER_ID)).willReturn(Optional.of(expectedCustomerDao));
        given(customerMapper.mapCustomer(expectedCustomerDao)).willReturn(expectedCustomer);

        List<Phonenumber> phonenumbers = customerService.getPhonenumbersByCustomerId(CUSTOMER_ID);

        assertThat(phonenumbers).usingRecursiveComparison().isEqualTo(expectedCustomer.getPhonenumbers());
    }

    @Test
    void shouldThrowResourceNotFoundException() {
        given(customerRepository.findById(CUSTOMER_ID)).willReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.getPhonenumbersByCustomerId(CUSTOMER_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer not found: 123");

    }


}
