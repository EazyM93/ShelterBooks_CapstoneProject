package ShelterBooks.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService us;
	
	// --------------------------------------------------------get all users
	@GetMapping("")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<User> getUser(){
		return us.getUsers();
	}
	
	// --------------------------------------------------------get current user
	@GetMapping("/getCurrent")
	public User getCurrentUser() {
		return us.getCurrentUser();
	}
	
	// --------------------------------------------------------find user by id
	@GetMapping("/idUser/{idUser}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public User findById(@PathVariable UUID idUser) {
		return us.findById(idUser);
	}
	
	// --------------------------------------------------------find user by email
	@GetMapping("/email/{email}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public User findByEmail(@PathVariable String email) {
		return us.findByEmail(email);
	}
	
	// --------------------------------------------------------add book to wishlist
	@PostMapping("/addWishlist/{idBook}")
	public User addWishlist(@PathVariable UUID idBook) {
		return us.addWishlist(idBook);
	}
	
	// --------------------------------------------------------remove book to wishlist
	@PostMapping("/removeWishlist/{idBook}")
	public User removeWishlist(@PathVariable UUID idBook) {
		return us.removeWishlist(idBook);
	}
	
	// --------------------------------------------------------update userby id
	@PutMapping("/{idUser}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public User updateUser(@PathVariable UUID idUser, 
			@RequestBody UserPayload body) {
		return us.findByIdAndUpdate(idUser, body);
	}
	
	// --------------------------------------------------------update current user
	@PutMapping("/updateCurrent")
	public User updateCurrentUser(@RequestBody UserPayload body) {
		return us.updateCurrentUser(body);
	}
	
	// --------------------------------------------------------delete user
	@DeleteMapping("/{idUser}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID idUser) {
		us.findByIdAndDelete(idUser);
	}
	
	// --------------------------------------------------------delete user
	@DeleteMapping("/deleteCurrent")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCurrentUser() {
		us.deleteCurrentUser();
	}
	
}
