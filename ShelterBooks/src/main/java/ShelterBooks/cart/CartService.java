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
		
		// extract the idCart of the current user through idUser
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
		
		// loops all the elemento of the HashMap
		for(Map.Entry<Book, Integer> entry: currentMap.entrySet()) {
			
			// check if the idBook passed is already in the cart by key Book
			// increase the quantity of copies by one if true
			if(entry.getKey().getIdBook().equals(book.getIdBook())) {
				entry.setValue(entry.getValue() + 1);
				return cartRepository.save(currentCart);
			}
			
		}
		
		// increase of one the copies of the same book in the cart
		currentMap.put(book, 1);
		return cartRepository.save(currentCart);
		
	}
	
	// --------------------------------------------------------remove book from cart
	public Cart removeBook(Cart currentCart, Book book) {
		
		Map<Book, Integer> currentMap = currentCart.getBooksWithQuantity();
		
		// check if there is only one copy left in cart, if true it removes the book
		if(currentMap.get(book) == 1) {
			currentMap.remove(book);
			return cartRepository.save(currentCart);
		}
		
		// decrease of one the copies of the same book in the cart
		currentMap.put(book, currentMap.get(book) - 1);
		return cartRepository.save(currentCart);
		
	}

	// --------------------------------------------------------clear cart
	public Cart clearCart(Cart currentCart) {
		
		Map<Book, Integer> currentMap = currentCart.getBooksWithQuantity();
		
		for(Map.Entry<Book, Integer> entry: currentMap.entrySet()) {
			
			// update number of copies after selling
			
			// update number all selled copies of the book
			
			// updadate user purchased books
			
		}
		
		// clear the cart
	}
	
}
