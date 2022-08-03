package au.com.belong.customerservice.api.data.repository;

import au.com.belong.customerservice.api.data.dao.Phonenumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhonenumberRepository extends JpaRepository<Phonenumber, String> {
    Phonenumber findByPhonenumberId(String phonenumberId);
}
