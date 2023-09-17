package ShelterBooks.book;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ShelterBooks.user.exceptions.NotFoundException;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	//-------------------------------------------------------------------------save book
	public Book saveBook(BookPayload body) {
		
		Book newBook = Book.builder()
				.isbn(body.getIsbn())
				.title(body.getTitle())
				.description(body.getDescription())
				.coverLink(body.getCoverLink())
				.author(body.getAuthor())
				.publisher(body.getPublisher())
				.pages(body.getPages())
				.price(body.getPrice())
				.publicationYear(body.getPublicationYear())
				.genre(body.getGenre())
				.availableEbook(body.getAvailableEbook())
				.insertionDate(LocalDate.now()) // generato alla creazione
				.availableCopies(body.getAvailableCopies())
				.ebookSize(body.getEbookSize())
				.ebookPrice(body.getEbookPrice())
				.allSelledCopies(0)
				.build();
		
		return bookRepository.save(newBook);
	}
	
	//-------------------------------------------------------------------------order book
	public Page<Book> findAll(int pageNumber, String sort){
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by(sort));
		return bookRepository.findAll(page);
	}
	
	//-------------------------------------------------------------------------find all books and get simple list
	public List<Book> getBooks(){
		return bookRepository.findAll();
	}
	
	//-------------------------------------------------------------------------find by id
	public Book findById(UUID idBook){
		return bookRepository.findById(idBook).get();
	}
	
	// ------------------------------------------------------------------------modify book by id
		public Book findByIdAndUpdate(UUID id, BookPayload body) throws NotFoundException {
			
			Book foundBook = this.findById(id);
			
			foundBook.setIsbn(body.getIsbn());
			foundBook.setTitle(body.getTitle());
			foundBook.setDescription(body.getDescription());
			foundBook.setCoverLink(body.getCoverLink());
			foundBook.setAuthor(body.getAuthor());
			foundBook.setPublisher(body.getPublisher());
			foundBook.setPages(body.getPages());
			foundBook.setPrice(body.getPrice());
			foundBook.setPublicationYear(body.getPublicationYear());
			foundBook.setGenre(body.getGenre());
			foundBook.setAvailableCopies(body.getAvailableCopies());
			foundBook.setAvailableEbook(body.getAvailableEbook());
			foundBook.setEbookSize(body.getEbookSize());
			foundBook.setEbookPrice(body.getEbookPrice());

			return bookRepository.save(foundBook);
		}
	
	//-------------------------------------------------------------------------modify copies
	public Book updateCopies(UpdatePayload body) {
		
		Book currentBook = bookRepository.findById(body.getIdBook()).get();
		
		currentBook.setAvailableCopies(currentBook.getAvailableCopies() + body.getNumberCopies());
		
		return bookRepository.save(currentBook);
	}
	
	//-------------------------------------------------------------------------modify selled copies
	public void updateSelledCopies(UUID idBook, int selledCopies) {
		
		Book currentBook = bookRepository.findById(idBook).get();
		
		currentBook.setAllSelledCopies(currentBook.getAllSelledCopies() + selledCopies);
		
		bookRepository.save(currentBook);
		
	}
	
	//-------------------------------------------------------------------------filter book
	public Page<Book> filterBooks(String isbn, String title, String author,
			String publisher, Double priceMin, Double priceMax,
			BookGenre genre, int pageNumber, String sort){
		
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by(sort));
		
		return bookRepository.searchBook(isbn, title, author, publisher,
				priceMin, priceMax, genre, page);
		
	} 
	
	//-------------------------------------------------------------------------delete book
	public void deleteBook(UUID idBook) {
		bookRepository.delete(this.findById(idBook));
	}
	
}
