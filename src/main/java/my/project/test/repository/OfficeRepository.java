package my.project.test.repository;

import my.project.test.entities.Customer;
import my.project.test.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office,String> {
    @Query("""
 select o from Office o where o.officeCode < ?1 and o.officeCode > ?2
""")
    List<Office> findOfficeByCodeMaxMin(String  max ,String min );
}
