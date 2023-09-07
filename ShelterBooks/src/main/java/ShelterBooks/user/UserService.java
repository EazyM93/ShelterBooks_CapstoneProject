package ShelterBooks.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ShelterBooks.cart.CartService;
import ShelterBooks.user.exceptions.BadRequestException;
import ShelterBooks.user.exceptions.NotFoundException;

@Service
public class UserService {

	@Autowired
	UserRepository ur;
	
	@Autowired
	CartService cs;
	
	// --------------------------------------------------------user save
	public User saveUser(UserPayload body) {
		
		ur.findByEmail(body.getEmail()).ifPresent(User -> {
			throw new BadRequestException("Email " + body.getEmail() + " è già stata utilizzata");
		});
		
		User newUser = User.builder()
				.name(body.getName())
				.surname(body.getSurname())
				.email(body.getEmail())
				.password(body.getPassword())
				.role(UserRole.USER)
				.build();
		
		cs.createCart(newUser);
		
		return ur.save(newUser);
		
	}
	
	// --------------------------------------------------------get all users
	public List<User> getUsers(){
		return ur.findAll();
	}
	
	// --------------------------------------------------------get user by id
	public User findById(UUID idUser) {
		return ur.findById(idUser)
				.orElseThrow(() -> new NotFoundException(idUser));
	}
	
	// --------------------------------------------------------get user by email
	public User findByEmail(String email) {
		return ur.findByEmail(email)
				.orElseThrow(() -> new NotFoundException(email));
	}
	
	// --------------------------------------------------------modify user by id
	public User findByIdAndUpdate(UUID id, UserPayload body) throws NotFoundException {
		
		User foundUser = this.findById(id);
		
		foundUser.setName(body.getName());
		foundUser.setSurname(body.getSurname());
		foundUser.setEmail(body.getEmail());
		
		return ur.save(foundUser);
	}
	
	// --------------------------------------------------------delete user by id
	public void findByIdAndDelete(UUID id) throws NotFoundException {
		User found = this.findById(id);
		ur.delete(found);
	}
}
