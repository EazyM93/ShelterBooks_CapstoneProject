package ShelterBooks.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPayload {

	// user attributes
	private String name;
	private String surname;
	private String email;
	private String password;
	
}
