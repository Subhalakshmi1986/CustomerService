package au.com.belong.customerservice.api.mapper;

import au.com.belong.customerservice.api.models.Phonenumber;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhonenumberMapper {

    Phonenumber mapPhonenumber(au.com.belong.customerservice.api.data.dao.Phonenumber phonenumberDao);

}
