package nsw.revenue.coding.challenge.repository;

import nsw.revenue.coding.challenge.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

}
