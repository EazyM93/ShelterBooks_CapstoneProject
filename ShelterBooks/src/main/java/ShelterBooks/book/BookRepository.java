package ShelterBooks.book;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID>{
	
	Optional<Book> findById(UUID idBook);
	
	@Query("SELECT b FROM Book b WHERE "
			+ "(:title IS NULL OR LOWER(b.title) LIKE LOWER(%:title%)) AND "
			+ "(:author IS NULL OR LOWER(b.author) LIKE LOWER(%:author%)) AND "
			+ "(:publisher IS NULL OR b.publisher = :publisher) AND "
			+ "(:priceMin IS NULL OR b.price >= :priceMin) AND "
			+ "(:priceMax IS NULL OR b.price <= :priceMax) AND "
			+ "(:genre IS NULL OR b.genre >= :genre) "
			+ "ORDER BY b.title")
	Page<Book> searchBook(
			@Param("title") String title,
			@Param("author") String author,
			@Param("publisher") String publisher,
			@Param("priceMin")Double priceMin,
			@Param("priceMax")Double priceMax,
			@Param("genre")BookGenre genre,
			Pageable page);
	
}
