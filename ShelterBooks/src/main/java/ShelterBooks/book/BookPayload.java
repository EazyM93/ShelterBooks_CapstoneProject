package ShelterBooks.book;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookPayload {

	private String isbn;
	private String title;
	private String description;
	private String coverLink;
	private String author;
	private String publisher;
	private int pages;
	private double price;
	private int publicationYear;
	@Enumerated(EnumType.STRING)
	private BookGenre genre;
	@Enumerated(EnumType.STRING)
	private EbookStatus availableEbook;
	private Integer availableCopies;
	private Integer ebookSize;
	private Double ebookPrice;
	
}
