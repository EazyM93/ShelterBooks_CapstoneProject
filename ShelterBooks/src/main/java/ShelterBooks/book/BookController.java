package ShelterBooks.book;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ShelterBooks.user.User;
import ShelterBooks.user.UserRepository;
import ShelterBooks.user.UserService;


@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	//------------------------------------------------------------------create book
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Book createBook(@RequestBody BookPayload body) {
		return bookService.saveBook(body);
	}
	
	// --------------------------------------------------------get book by id
	@GetMapping("/idBook/{idBook}")
	public Book findById(@PathVariable UUID idBook) {
		return bookService.findById(idBook);
	}
	
	//------------------------------------------------------------------get all books
	@GetMapping("/getAllBooks")
	public List<Book> getAllBooks(){
		return bookService.getBooks();
	}
	
	//------------------------------------------------------------------get all books & order
	@GetMapping("")
	public Page<Book> findAll(
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "insertionDate") String sort
	){
		return bookService.findAll(pageNumber, sort);
	}
	
	//------------------------------------------------------------------update books copies
	@PostMapping("/updateCopies")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Book updateCopies(@RequestBody UpdatePayload body) {
		return bookService.updateCopies(body);
	}
	
	//------------------------------------------------------------------update book
	@PutMapping("/{idBook}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Book updateUser(@PathVariable UUID idBook, 
			@RequestBody BookPayload body) {
		return bookService.findByIdAndUpdate(idBook, body);
	}
	
	//------------------------------------------------------------------filter books
	@GetMapping("/filter")
	public Page<Book> filters(
			@RequestParam(required = false) String isbn,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String author,
			@RequestParam(required = false) String publisher,
			@RequestParam(required = false) Double priceMin,
			@RequestParam(required = false) Double priceMax,
			@RequestParam(required = false) BookGenre genre,
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "title") String sort
	){	
		
		return bookService.filterBooks(isbn, title, author, publisher,
				priceMin, priceMax, genre, pageNumber, sort);
	}
	
	// --------------------------------------------------------delete book
	@DeleteMapping("/{idBook}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBook(@PathVariable UUID idBook) {
		
		for(User user : userService.getUsers()) {
			
			List<Book> actualList = user.getWishlist();
			
			for(Book book : actualList) {
				if(book.getIdBook().equals(idBook)) {
					user.getWishlist().remove(book);
					userRepository.save(user);
				}
					
			}
		}
		
		bookService.deleteBook(idBook);
	}
}
