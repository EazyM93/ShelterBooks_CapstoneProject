package ShelterBooks.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ShelterBooks.book.Book;
import ShelterBooks.user.User;
import ShelterBooks.user.exceptions.NotFoundException;

@Service
public class CartService {

	@Autowired
	CartRepository cartRepository;
	
	//-------------------------------------------------------------------------save cart
	public Cart createCart(User user){
		
		Cart newCart = Cart.builder()
				.user(user)
				.books(new ArrayList<>())
				.build();
		
		return cartRepository.save(newCart);
	}
	
	// --------------------------------------------------------get all carts
	public List<Cart> getCarts(){
		return cartRepository.findAll();
	}
	
	// --------------------------------------------------------get cart by id
	public Cart findById(UUID idCart) {
		return cartRepository.findById(idCart)
				.orElseThrow(() -> new NotFoundException(idCart));
	}
	
	// --------------------------------------------------------find cart by user id
	public Cart findByCurrentUser(User currentUser){
		
		UUID idCart = null;
				
		for(Cart c : getCarts()) {
			if(c.getUser().getIdUser().equals(currentUser.getIdUser())) {
				idCart = c.getIdCart();
				break;
			}
		}
		
		return findById(idCart);
		
	}
	
	// --------------------------------------------------------add book to cart
	public void addBook(Cart currentCart, Book book) {
		currentCart.getBooks().add(book);
		cartRepository.save(currentCart);
	}
	
}
