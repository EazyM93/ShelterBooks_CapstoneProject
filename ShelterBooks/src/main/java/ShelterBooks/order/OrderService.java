package ShelterBooks.order;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ShelterBooks.book.Book;
import ShelterBooks.cart.Cart;
import ShelterBooks.cart.CartService;
import ShelterBooks.user.User;
import ShelterBooks.user.UserService;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CartService cartService;
	
	//-------------------------------------------------------------------------create order
	public Order createOrder() {
		
		User currentUser = userService.getCurrentUser();
		
		Cart currentCart = cartService.findByCurrentUser(currentUser);
		
		Map<Book, Integer> currentMap = currentCart.getBooksWithQuantity();
		
		Order newOrder = Order.builder()
				.user(currentUser)
				.submitted(LocalDate.now())
				.booksWithQuantity(currentMap)
				.build();
		
		return orderRepository.save(newOrder);
	}
	
	//-------------------------------------------------------------------------find order
	public Order findById(UUID idOrder) {
		return orderRepository.findById(idOrder).get();
	}
	
	//-------------------------------------------------------------------------create order
	public Page<Order> findAll(int pageNumber, String sort){
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by(sort));
		return orderRepository.findAll(page);
	}
	
	//-------------------------------------------------------------------------deploy order
	public Order deployOrder(UUID idOrder) {
		
		Order deploiedOrder = findById(idOrder);
		
		deploiedOrder.setShipped(LocalDate.now());
		
		return orderRepository.save(deploiedOrder);
	}
	
	//-------------------------------------------------------------------------delete order
	public void deleteOrder(UUID idOrder) {
		orderRepository.delete(findById(idOrder));		
	}
}
