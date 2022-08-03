package au.com.belong.customerservice.api.data.repository;

import au.com.belong.customerservice.api.data.dao.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
