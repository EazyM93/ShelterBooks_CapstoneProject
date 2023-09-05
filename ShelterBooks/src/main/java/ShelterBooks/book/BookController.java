package ShelterBooks.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	//------------------------------------------------------------------create book
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Book createBook(@RequestBody BookPayload body) {
		Book createdBook = bookService.saveBook(body);
		return createdBook;
	}
	
	//------------------------------------------------------------------get all books & order
	@GetMapping("")
	public Page<Book> findAll(
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "insertionDate") String sort
	){
		return bookService.findAll(pageNumber, sort);
	}
	
	//------------------------------------------------------------------filter books
	@GetMapping("/filter")
	public Page<Book> filters(
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String author,
			@RequestParam(required = false) String publisher,
			@RequestParam(required = false) Double priceMin,
			@RequestParam(required = false) Double priceMax,
			@RequestParam(required = false) BookGenre genre,
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "title") String sort
	){	
		
		return bookService.filterBooks(title, author, publisher,
				priceMin, priceMax, genre, pageNumber, sort);
	}
}
