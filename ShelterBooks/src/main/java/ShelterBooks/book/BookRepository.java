package ShelterBooks.book;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ShelterBooks.user.User;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID>{
	
	Optional<Book> findById(UUID idBook);
}
