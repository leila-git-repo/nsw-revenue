package nsw.revenue.coding.challenge.repository;


import nsw.revenue.coding.challenge.model.VehicleRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRegistrationRepository extends JpaRepository<VehicleRegistration, String> {


}
