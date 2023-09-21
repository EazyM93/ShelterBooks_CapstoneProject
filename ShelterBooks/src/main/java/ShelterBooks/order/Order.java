package ShelterBooks.order;

import java.time.LocalDate;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	@Id
	@GeneratedValue
	private UUID idOrder;
	
	@ManyToOne
	@JoinColumn(name = "idUser")
	private User user;
	
	@Column
	private LocalDate submitted;
	
	@Column
	private LocalDate shipped;
	
	@ElementCollection
    @CollectionTable(name = "order_books")
    @MapKeyJoinColumn(name = "idBook")
    @Column(name = "bookQuantity")
    private Map<Book, Integer> shoppedBooksWithQuantity;
	
}
