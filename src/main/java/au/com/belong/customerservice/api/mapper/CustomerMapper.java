package au.com.belong.customerservice.api.mapper;

import au.com.belong.customerservice.api.models.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PhonenumberMapper.class})
public interface CustomerMapper {

    Customer mapCustomer(au.com.belong.customerservice.api.data.dao.Customer customerDao);

}
