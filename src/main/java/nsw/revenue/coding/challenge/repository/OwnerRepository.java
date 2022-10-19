package nsw.revenue.coding.challenge.repository;

import java.util.List;
import nsw.revenue.coding.challenge.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, String> {

    List<Owner> findByUsername(String username);
}
