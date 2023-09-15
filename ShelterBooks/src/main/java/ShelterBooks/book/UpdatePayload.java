package ShelterBooks.book;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePayload {

	private UUID idBook;
	private int numberCopies;
	
}
