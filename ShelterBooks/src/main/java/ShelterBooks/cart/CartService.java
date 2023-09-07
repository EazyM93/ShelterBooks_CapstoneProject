package ShelterBooks.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
				.booksWithQuantity(new HashMap<>())
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
	public Cart addBook(Cart currentCart, Book book) {
		
		Map<Book, Integer> currentMap = currentCart.getBooksWithQuantity();
		
		for(Map.Entry<Book, Integer> entry: currentMap.entrySet()) {
			
			if(entry.getKey().getIdBook().equals(book.getIdBook())) {
				entry.setValue(entry.getValue() + 1);
				return cartRepository.save(currentCart);
			}
			
		}
		
		currentMap.put(book, 1);
		return cartRepository.save(currentCart);
		
	}
	
	// --------------------------------------------------------remove book from cart
	public Cart removeBook(Cart currentCart, Book book) {
		
		Map<Book, Integer> currentMap = currentCart.getBooksWithQuantity();
		
		if(currentMap.get(book) == 1) {
			currentMap.remove(book);
			return cartRepository.save(currentCart);
		}
			
		currentMap.put(book, currentMap.get(book) - 1);
		return cartRepository.save(currentCart);
		
	}

}
