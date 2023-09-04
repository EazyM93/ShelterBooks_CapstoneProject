package ShelterBooks.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public List<User> getUser(){
		return us.getUsers();
	}
	
	// --------------------------------------------------------find user by id
	@GetMapping("/{idUser}")
	public User findById(@PathVariable UUID idUser) {
		return us.findById(idUser);
	}
	
	// --------------------------------------------------------find user by email
	@GetMapping("/{email}")
	public User findById(@PathVariable String email) {
		return us.findByEmail(email);
	}
	
	// --------------------------------------------------------update user
	@PutMapping("/{idUser}")
	public User updateUser(@PathVariable UUID idUser, @RequestBody UserPayload body) {
		return us.findByIdAndUpdate(idUser, body);
	}
	
	// --------------------------------------------------------delete user
	@DeleteMapping("/{idUser}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID idUser) {
		us.findByIdAndDelete(idUser);
	}
	
}
