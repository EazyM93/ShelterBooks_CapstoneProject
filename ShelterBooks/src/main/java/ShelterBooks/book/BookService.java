package ShelterBooks.book;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	//-------------------------------------------------------------------------save book
	public Book saveBook(BookPayload body) {
		
		Book newBook = Book.builder()
				.isbn(body.getIsbn())
				.title(body.getTitle())
				.author(body.getAuthor())
				.publisher(body.getPublisher())
				.pages(body.getPages())
				.price(body.getPrice())
				.publicationYear(body.getPublicationYear())
				.genre(body.getGenre())
				.avaliableEbook(body.getAvaliableEbook())
				.insertionDate(LocalDate.now()) // generato alla creazione
				.avaliableCopies(body.getAvaliableCopies())
				.ebookSize(body.getEbookSize())
				.ebookPrice(body.getEbookPrice())
				.build();
		
		return bookRepository.save(newBook);
	}
	
	//-------------------------------------------------------------------------order book
	public Page<Book> findAll(int pageNumber, String order){
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by(order));
		return bookRepository.findAll(page);
	}
	
}
