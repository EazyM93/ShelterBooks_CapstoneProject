package ShelterBooks.book;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

	@Id
	@GeneratedValue
	private UUID idBook;
	
	@Column(nullable = false, unique = true)
	private String isbn;
	
	@Column(nullable = false, unique = true)
	private String title;
	
	@Column(nullable = false, unique = true)
	private String coverLink;
	
	@Column(nullable = false)
	private String author;
	
	@Column(nullable = false)
	private String publisher;
	
	@Column(nullable = false)
	private int pages;
	
	@Column(nullable = false)
	private double price;
	
	@Column(nullable = false)
	private int publicationYear;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private BookGenre genre;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EbookStatus availableEbook;
	
	// attributi inizializzati dopo l'inserimento dei dati
	@Column(nullable = false)
	private LocalDate insertionDate;
	
	// i seguenti attributi non hanno nullable e usano le reference come tipo.
	// questo permette di creare oggetti con questi valori null
	@Column
	private Integer availableCopies;
	
	@Column
	private Integer ebookSize;
	
	@Column
	private Double ebookPrice;
	
	@Column
	private Integer allSelledCopies;
	
}
