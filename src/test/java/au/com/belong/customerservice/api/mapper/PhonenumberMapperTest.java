package au.com.belong.customerservice.api.mapper;

import au.com.belong.customerservice.api.models.Phonenumber;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static au.com.belong.customerservice.api.fixture.CustomerFixture.getPhonenumbers;
import static au.com.belong.customerservice.api.fixture.CustomerFixture.getPhonenumbersDao;
import static org.assertj.core.api.Assertions.assertThat;

class PhonenumberMapperTest {

    private final PhonenumberMapper phonenumberMapper = Mappers.getMapper(PhonenumberMapper.class);

    @Test
    void shouldMapPhoneNumber() {
        Phonenumber phonenumber = phonenumberMapper.mapPhonenumber(getPhonenumbersDao().get(0));

        assertThat(phonenumber).usingRecursiveComparison().isEqualTo(getPhonenumbers().get(0));
    }

}
