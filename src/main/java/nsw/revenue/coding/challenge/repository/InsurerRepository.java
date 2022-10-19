package nsw.revenue.coding.challenge.repository;

import nsw.revenue.coding.challenge.model.Insurer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsurerRepository extends JpaRepository<Insurer, String> {

}
