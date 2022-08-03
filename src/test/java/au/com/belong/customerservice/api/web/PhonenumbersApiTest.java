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
class PhonenumbersApiTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldReturnPhonenumberById() {
        Phonenumber expectedPhonenumber =
                new Phonenumber().status(Phonenumber.StatusEnum.ACTIVE).phonenumberId("469324654");

        ResponseEntity<Phonenumber> responseEntity = this.testRestTemplate.getForEntity("/phonenumbers"
                + "/{phonenumberId}", Phonenumber.class, "469324654");
        Phonenumber phonenumber = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(phonenumber).usingRecursiveComparison().isEqualTo(expectedPhonenumber);
    }

    @Test
    void shouldReturnNotFoundWhenInvalidPhonenumber() {
        ServiceError expectedServiceError =
                new ServiceError()
                        .error("RES_001")
                        .message("phonenumber not found: 789345678")
                        .detail("Resource not found.Please query valid resource.");

        ResponseEntity<ServiceError> responseEntity = this.testRestTemplate.getForEntity("/phonenumbers/"
                + "{phonenumberId}", ServiceError.class, "789345678");
        ServiceError error = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(error).usingRecursiveComparison().isEqualTo(expectedServiceError);
    }

    @Test
    void shouldReturnBadRequestWhenInvalidPatternPhonenumber() {
        ServiceError expectedServiceError =
                new ServiceError()
                        .error("VAL_001")
                        .message("getPhonenumberById.phonenumberId: must match \"^\\d{9}$\"")
                        .detail("Please send a valid request.");

        ResponseEntity<ServiceError> responseEntity = this.testRestTemplate.getForEntity("/phonenumbers/"
                + "{phonenumberId}", ServiceError.class, "789");
        ServiceError error = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(error).usingRecursiveComparison().isEqualTo(expectedServiceError);
    }

    @Test
    void shouldReturnPhonenumbers() {
        Phonenumber[] expectedPhonenumbers =
                {new Phonenumber().status(Phonenumber.StatusEnum.ACTIVE).phonenumberId("469324654"),
                        new Phonenumber().status(Phonenumber.StatusEnum.ACTIVE).phonenumberId("469324641"),
                        new Phonenumber().status(Phonenumber.StatusEnum.INACTIVE).phonenumberId("469324642"),
                        new Phonenumber().status(Phonenumber.StatusEnum.INACTIVE).phonenumberId("469324643"),
                        new Phonenumber().status(Phonenumber.StatusEnum.INACTIVE).phonenumberId("469324644"),
                        new Phonenumber().status(Phonenumber.StatusEnum.INACTIVE).phonenumberId("469324645"),
                        new Phonenumber().status(Phonenumber.StatusEnum.INACTIVE).phonenumberId("469324646"),
                        new Phonenumber().status(Phonenumber.StatusEnum.INACTIVE).phonenumberId("469324647"),
                        new Phonenumber().status(Phonenumber.StatusEnum.INACTIVE).phonenumberId("469324648")};

        ResponseEntity<Phonenumber[]> responseEntity = this.testRestTemplate.getForEntity("/phonenumbers"
                , Phonenumber[].class);
        Phonenumber[] phonenumber = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(phonenumber).usingRecursiveComparison().isEqualTo(expectedPhonenumbers);
    }

    @Test
    void shouldActivatePhonenumberById() {
        Phonenumber expectedPhonenumber =
                new Phonenumber().status(Phonenumber.StatusEnum.INACTIVE).phonenumberId("469324642");

        Phonenumber phonenumber = testRestTemplate.patchForObject("/phonenumbers/{phonenumberId}",
                new Phonenumber().status(Phonenumber.StatusEnum.INACTIVE), Phonenumber.class, "469324642");

        assertThat(phonenumber).usingRecursiveComparison().isEqualTo(expectedPhonenumber);
    }

    @Test
    void shouldReturnNotFoundWhenInvalidPhonenumberIsActivated() {
        ServiceError expectedServiceError =
                new ServiceError()
                        .error("RES_001")
                        .message("phonenumber not found: 789345678")
                        .detail("Resource not found.Please query valid resource.");

        ServiceError error = this.testRestTemplate.patchForObject("/phonenumbers/"
                        + "{phonenumberId}", new Phonenumber().status(Phonenumber.StatusEnum.INACTIVE),
                ServiceError.class,
                "789345678");

        assertThat(error).usingRecursiveComparison().isEqualTo(expectedServiceError);
    }

    @Test
    void shouldReturnBadRequestWhenInvalidPatternPhonenumberIsActivated() {
        ServiceError expectedServiceError =
                new ServiceError()
                        .error("VAL_001")
                        .message("activatePhonenumber.phonenumberId: must match \"^\\d{9}$\"")
                        .detail("Please send a valid request.");

        ServiceError error = this.testRestTemplate.patchForObject("/phonenumbers/"
                        + "{phonenumberId}", new Phonenumber().status(Phonenumber.StatusEnum.INACTIVE),
                ServiceError.class,
                "789");

        assertThat(error).usingRecursiveComparison().isEqualTo(expectedServiceError);
    }
}
