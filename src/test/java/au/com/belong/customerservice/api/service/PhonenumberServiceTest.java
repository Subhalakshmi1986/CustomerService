package au.com.belong.customerservice.api.service;

import au.com.belong.customerservice.api.data.dao.Phonenumber;
import au.com.belong.customerservice.api.data.repository.PhonenumberRepository;
import au.com.belong.customerservice.api.exception.ResourceNotFoundException;
import au.com.belong.customerservice.api.mapper.PhonenumberMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static au.com.belong.customerservice.api.fixture.CustomerFixture.PHONENUMBER_ID_1;
import static au.com.belong.customerservice.api.fixture.CustomerFixture.getPhonenumbers;
import static au.com.belong.customerservice.api.fixture.CustomerFixture.getPhonenumbersDao;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PhonenumberServiceTest {

    @InjectMocks
    private PhonenumberService phonenumberService;

    @Mock
    private PhonenumberRepository phonenumberRepository;

    @Mock
    private PhonenumberMapper phonenumberMapper;

    @Test
    void shouldGetAllPhonenumbers() {
        given(phonenumberRepository.findAll()).willReturn(getPhonenumbersDao());
        given(phonenumberMapper.mapPhonenumber(any())).willReturn(getPhonenumbers().get(0));

        List<au.com.belong.customerservice.api.models.Phonenumber> phonenumbers = phonenumberService.getPhonenumbers();

        assertThat(phonenumbers).usingRecursiveComparison().isEqualTo(getPhonenumbers());
    }

    @Test
    void shouldGetPhonenumberById() {
        au.com.belong.customerservice.api.models.Phonenumber expectedPhonenumber = getPhonenumbers().get(0);
        Optional<Phonenumber> optional = Optional.ofNullable(getPhonenumbersDao().get(0));
        given(phonenumberRepository.findById(PHONENUMBER_ID_1)).willReturn(optional);
        given(phonenumberMapper.mapPhonenumber(optional.get())).willReturn(expectedPhonenumber);

        au.com.belong.customerservice.api.models.Phonenumber phonenumber =
                phonenumberService.getPhonenumberById(PHONENUMBER_ID_1);

        assertThat(phonenumber).usingRecursiveComparison().isEqualTo(expectedPhonenumber);
    }

    @Test
    void shouldThrowResourceNotFoundException() {
        given(phonenumberRepository.findById(PHONENUMBER_ID_1)).willReturn(Optional.empty());

        assertThatThrownBy(() -> phonenumberService.getPhonenumberById(PHONENUMBER_ID_1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("phonenumber not found: 213142356");
    }

    @Test
    void shouldActivatePhonenumber() {
        Phonenumber expectedPhonenumberDao = getPhonenumbersDao().get(0);
        expectedPhonenumberDao.setStatus("INACTIVE");
        au.com.belong.customerservice.api.models.Phonenumber inputPhonenumber =
                getPhonenumbers().get(0)
                        .status(au.com.belong.customerservice.api.models.Phonenumber.StatusEnum.INACTIVE);
        Phonenumber existingPhonenumberDao = getPhonenumbersDao().get(0);
        given(phonenumberRepository.findByPhonenumberId(PHONENUMBER_ID_1)).willReturn(existingPhonenumberDao);
        given(phonenumberRepository.save(any())).willReturn(expectedPhonenumberDao);
        given(phonenumberMapper.mapPhonenumber(expectedPhonenumberDao)).willReturn(inputPhonenumber);

        au.com.belong.customerservice.api.models.Phonenumber phonenumber =
                phonenumberService.activatePhonenumber(PHONENUMBER_ID_1, inputPhonenumber);

        assertThat(phonenumber).usingRecursiveComparison().isEqualTo(inputPhonenumber);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionForInvalidNumber() {
        given(phonenumberRepository.findByPhonenumberId(PHONENUMBER_ID_1)).willReturn(null);
        au.com.belong.customerservice.api.models.Phonenumber phonenumber = getPhonenumbers().get(0);
        assertThatThrownBy(() -> phonenumberService.activatePhonenumber(PHONENUMBER_ID_1, phonenumber))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("phonenumber not found: 213142356");
    }
}
