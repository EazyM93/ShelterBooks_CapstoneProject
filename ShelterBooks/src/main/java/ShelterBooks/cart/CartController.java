package ShelterBooks.cart;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ShelterBooks.book.BookService;
import ShelterBooks.user.UserService;

@RestController
@RequestMapping("/carts")
public class CartController {

	@Autowired
	CartService cartService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BookService bookService;
	
	// --------------------------------------------------------find cart by user id
	@GetMapping("/currentUser")
	public Cart findByCurrentUser() {
		return cartService.findByCurrentUser(userService.getCurrentUser());		
	}
	
	// --------------------------------------------------------add book to cart
	@PostMapping("/addBook/{idBook}")
	public Cart addBook(@PathVariable UUID idBook) {
		return cartService.addBook(findByCurrentUser(), bookService.findById(idBook));
	}
	
	// --------------------------------------------------------add book to cart
	@PostMapping("/removeBook/{idBook}")
	public Cart removeBook(@PathVariable UUID idBook) {
		return cartService.removeBook(findByCurrentUser(), bookService.findById(idBook));
	}
	
	// --------------------------------------------------------add book to cart
	@PostMapping("/clearCart")
	public Cart clearCart() {
		return cartService.clearCart(userService.getCurrentUser());
	}
	
}
