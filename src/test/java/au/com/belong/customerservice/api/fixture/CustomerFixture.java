package au.com.belong.customerservice.api.fixture;

import au.com.belong.customerservice.api.data.dao.Customer;
import au.com.belong.customerservice.api.data.dao.Phonenumber;

import java.util.ArrayList;
import java.util.List;

public final class CustomerFixture {
    public static final String PHONENUMBER_ID_1 = "213142356";
    public static final Long CUSTOMER_ID = 123L;

    private CustomerFixture() {
    }

    public static Customer getCustomerDao() {
        List<Phonenumber> phoneNumbers = getPhonenumbersDao();
        return new Customer(CUSTOMER_ID, "Subha", phoneNumbers);

    }

    public static List<Phonenumber> getPhonenumbersDao() {
        List<Phonenumber> phonenumbers = new ArrayList<>();
        phonenumbers.add(new Phonenumber(PHONENUMBER_ID_1, "ACTIVE", new Customer()));
        return phonenumbers;

    }

    public static List<au.com.belong.customerservice.api.models.Phonenumber> getPhonenumbers() {
        List<au.com.belong.customerservice.api.models.Phonenumber> phonenumbers = new ArrayList<>();
        au.com.belong.customerservice.api.models.Phonenumber phonenumber1 =
                new au.com.belong.customerservice.api.models.Phonenumber();
        phonenumber1.setPhonenumberId(PHONENUMBER_ID_1);
        phonenumber1.setStatus(au.com.belong.customerservice.api.models.Phonenumber.StatusEnum.ACTIVE);
        phonenumbers.add(phonenumber1);
        return phonenumbers;

    }

    public static au.com.belong.customerservice.api.models.Customer getCustomer() {
        au.com.belong.customerservice.api.models.Customer customer =
                new au.com.belong.customerservice.api.models.Customer();
        customer.setCustomerId(CUSTOMER_ID);
        customer.setPhonenumbers(getPhonenumbers());
        return customer;
    }

}
