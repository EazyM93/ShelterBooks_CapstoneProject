package ShelterBooks.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ShelterBooks.book.Book;
import ShelterBooks.book.BookService;
import ShelterBooks.user.User;
import ShelterBooks.user.UserRepository;
import ShelterBooks.user.exceptions.NotFoundException;

@Service
public class CartService {

	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BookService bookService;
	
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
	public Cart clearCart(User user) {
		
		// current cart of the user
		Cart currentCart = findByCurrentUser(user);
		
		// current cart's elements
		Map<Book, Integer> currentMap = currentCart.getBooksWithQuantity();
		
		// current books list of the store
		List<Book> bookList = bookService.getBooks();
		
		// empty list to store all the matches to be add to the user
		List<Book> booksToAdd = new ArrayList<>();
		
		// current list of purchased books by the user
		List <Book> userBooks = user.getPurchasedBooks();
		
		// loop current cart
		for(Map.Entry<Book, Integer> entry: currentMap.entrySet()) {
			
			UUID actualBook = entry.getKey().getIdBook();
			int actualQuantity = entry.getValue();
			
			// loop all books in storage
			for(Book book : bookList) {
				
				if(book.getIdBook().equals(actualBook)){
					
					// update number of available copies after selling
					book.setAvailableCopies(book.getAvailableCopies() - actualQuantity);
			
					// update number all selled copies of the book
					book.setAllSelledCopies(book.getAllSelledCopies() + actualQuantity);
			
					// updadate user purchased books only if it is not present in the list
					boolean idMatch = false;
					
					for(Book b : userBooks) {
						if(!book.getIdBook().equals(b.getIdBook())) {
							idMatch = true;
							break;
						}	
					}
		
					if(!idMatch)
						booksToAdd.add(book);
					
					// stop inner loop after finding the match and restart with another element
					break;
				}
			}
		}
		
		// add books and save user 
		userBooks.addAll(booksToAdd);
		userRepository.save(user);
		
		// clear cart
		currentMap.clear();
		return cartRepository.save(currentCart);
		
	}
	
	// --------------------------------------------------------delete cart from user
	public void deleteFromCurrentUser(User currentUser) {
		
		Cart currentCart = this.findByCurrentUser(currentUser);
		
		cartRepository.delete(currentCart); 
	}
	
}
