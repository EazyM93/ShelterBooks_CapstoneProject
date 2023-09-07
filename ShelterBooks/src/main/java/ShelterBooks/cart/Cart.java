package ShelterBooks.cart;

import java.util.List;
import java.util.UUID;

import ShelterBooks.book.Book;
import ShelterBooks.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carts")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

	@Id
	@GeneratedValue
	private UUID idCart;
	
	@OneToOne
	@JoinColumn(name = "idUser")
	private User user;
	
	@OneToMany
	private List<Book> books;
	
}
