package ShelterBooks.order;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository  extends JpaRepository<Order, UUID>{

	Optional<Order> findById(UUID idOrder);
	
}
