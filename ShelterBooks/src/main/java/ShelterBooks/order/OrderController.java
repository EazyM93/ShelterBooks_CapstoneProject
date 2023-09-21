package ShelterBooks.order;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	// --------------------------------------------------------create order
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Order createBook() {
		return orderService.createOrder();
	}
	
	// --------------------------------------------------------find order
	@GetMapping("/idOrder/{idOrder}")
	public Order findById(@PathVariable UUID idOrder) {
		return orderService.findById(idOrder);
	}

	// --------------------------------------------------------get all orders
	@GetMapping("")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Page<Order> findAll(
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "submitted") String sort
	){
		return orderService.findAll(pageNumber, sort);
	}
	
	// --------------------------------------------------------deploy order
	@PostMapping("/shipOrder/{idOrder}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Order deployOrder(@PathVariable UUID idOrder) {
		return orderService.deployOrder(idOrder);
	}
	
	// --------------------------------------------------------ship order
	@DeleteMapping("/{idOrder}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOrder(@PathVariable UUID idOrder) {
		orderService.deleteOrder(idOrder);
	}
	
}