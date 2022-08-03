package au.com.belong.customerservice.api.web;

import au.com.belong.customerservice.api.models.Phonenumber;
import au.com.belong.customerservice.api.models.ServiceError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomersApiTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldReturnPhonenumberByCustomerId() {
        Phonenumber[] expectedPhonenumbers =
                {new Phonenumber().status(Phonenumber.StatusEnum.ACTIVE).phonenumberId("469324654"),
                        new Phonenumber().status(Phonenumber.StatusEnum.ACTIVE).phonenumberId("469324641"),
                        new Phonenumber().status(Phonenumber.StatusEnum.INACTIVE).phonenumberId("469324642")};

        ResponseEntity<Phonenumber[]> responseEntity = this.testRestTemplate.getForEntity("/customers/{customerId"
                + "}/phonenumbers", Phonenumber[].class, "123");
        Phonenumber[] phonenumbers = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(phonenumbers).usingRecursiveComparison().isEqualTo(expectedPhonenumbers);
    }

    @Test
    void shouldReturnNotFound() {
        ServiceError expectedServiceError =
                new ServiceError().error("RES_001").message("customer not found: 789").detail("Resource not found"
                        + ".Please query valid resource.");

        ResponseEntity<ServiceError> responseEntity = this.testRestTemplate.getForEntity("/customers/{customerId"
                + "}/phonenumbers", ServiceError.class, "789");
        ServiceError error = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(error).usingRecursiveComparison().isEqualTo(expectedServiceError);

    }

}

