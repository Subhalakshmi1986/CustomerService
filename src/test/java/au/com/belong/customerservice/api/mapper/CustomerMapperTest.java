package au.com.belong.customerservice.api.mapper;


import au.com.belong.customerservice.api.data.dao.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static au.com.belong.customerservice.api.fixture.CustomerFixture.getCustomerDao;
import static au.com.belong.customerservice.api.fixture.CustomerFixture.getPhonenumbers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomerMapperTest {

    @InjectMocks
    private final CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    @Mock
    private PhonenumberMapper phonenumberMapper;

    @Test
    void shouldMapCustomer() {
        Customer customerDao = getCustomerDao();
        given(phonenumberMapper.mapPhonenumber(any())).willReturn(getPhonenumbers().get(0));
        au.com.belong.customerservice.api.models.Customer expectedCustomer =
                new au.com.belong.customerservice.api.models.Customer().customerId(
                        customerDao.getCustomerId()).phonenumbers(getPhonenumbers());

        au.com.belong.customerservice.api.models.Customer customer = customerMapper.mapCustomer(customerDao);

        assertThat(customer).usingRecursiveComparison().isEqualTo(expectedCustomer);
    }
}
