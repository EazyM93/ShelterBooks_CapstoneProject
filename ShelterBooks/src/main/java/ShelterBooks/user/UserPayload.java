package ShelterBooks.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPayload {

	// user attributes
	private String image;
	private String name;
	private String surname;
	private String email;
	private String password;
	
	// address attributes
	private String addressName;
	private String postalCode;
	private String city;
	private String district;
	private String country;
	
}
