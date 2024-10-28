package my.project.test.repository;

 import my.project.test.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Query;

 import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
 List<Customer> findCustomersByCustomerNameContaining(String customerName);

}
