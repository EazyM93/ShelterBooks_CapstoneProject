package ShelterBooks.cart;

import java.util.Map;
import java.util.UUID;

import ShelterBooks.book.Book;
import ShelterBooks.user.User;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
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
	
	@ElementCollection
    @CollectionTable(name = "cart_books")
    @MapKeyJoinColumn(name = "idBook")
    @Column(name = "bookQuantity")
    private Map<Book, Integer> booksWithQuantity;
	
}
