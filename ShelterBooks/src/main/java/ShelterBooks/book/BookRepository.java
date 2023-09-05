package ShelterBooks.book;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID>{
	
	Optional<Book> findById(UUID idBook);
	
	@Query("SELECT b FROM Book b WHERE "
			+ "(?1 IS NULL OR b.title LIKE %?1%) AND " // cerca anche solo parte del titolo
			+ "(?2 IS NULL OR b.author = ?2) AND "
			+ "(?3 IS NULL OR b.publisher = ?3) AND "
			+ "(?4 IS NULL OR b.price >= ?4) AND "
			+ "(?5 IS NULL OR b.price <= ?5) AND "
			+ "(?6 IS NULL OR b.genre >= ?6) "
			+ "ORDER BY b.title")
	Page<Book> searchBook(String title, String author, String publisher,
			Double priceMin, Double priceMax, BookGenre genre, Pageable page);
	
}
