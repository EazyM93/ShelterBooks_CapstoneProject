package ShelterBooks.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ShelterBooks.address.Address;
import ShelterBooks.address.AddressRepository;
import ShelterBooks.book.BookRepository;
import ShelterBooks.cart.CartService;
import ShelterBooks.user.exceptions.BadRequestException;
import ShelterBooks.user.exceptions.NotFoundException;

@Service
public class UserService {

	@Autowired
	UserRepository ur;
	
	@Autowired
	BookRepository br;
	
	@Autowired
	AddressRepository ar;
	
	@Autowired
	CartService cs;
	
	// --------------------------------------------------------user save
	public User saveUser(UserPayload body) {
		
		// check if current mail is already present
		ur.findByEmail(body.getEmail()).ifPresent(User -> {
			throw new BadRequestException("Email " + body.getEmail() + " è già stata utilizzata");
		});
		
		// create new address
		Address newAddress = Address.builder()
				.addressName(body.getAddressName())
				.postalCode(body.getPostalCode())
				.city(body.getCity())
				.district(body.getDistrict())
				.country(body.getCountry())
				.build();
		
		Address savedAddress = ar.save(newAddress);
		
		// create new user
		User newUser = User.builder()
				.image(body.getImage())
				.name(body.getName())
				.surname(body.getSurname())
				.email(body.getEmail())
				.password(body.getPassword())
				.role(UserRole.USER)
				.address(ar.findById(savedAddress.getIdAddress()).get())
				.build();
		
		ur.save(newUser);
		
		// create user's cart
		cs.createCart(newUser);
		
		return newUser;
		
	}
	
	// --------------------------------------------------------get all users
	public List<User> getUsers(){
		return ur.findAll();
	}
	
	// --------------------------------------------------------get current user
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		User found = ur.findByEmail(currentUserName)
				.orElseThrow(() -> new NotFoundException("Utente con email " + currentUserName + " non trovato"));
		System.out.println("Utente trovato: " + found.getName());
		return found;
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
		
		foundUser.setImage(body.getImage());
		foundUser.setName(body.getName());
		foundUser.setSurname(body.getSurname());
		foundUser.setEmail(body.getEmail());
		foundUser.setPassword(body.getPassword());
		
		return ur.save(foundUser);
	}
	
	// --------------------------------------------------------modify current user 
	public User updateCurrentUser(UserPayload body) throws NotFoundException {
			
		User foundUser = getCurrentUser();
		
		Address modAddress = foundUser.getAddress();
		
		modAddress.setAddressName(body.getAddressName());
		modAddress.setPostalCode(body.getPostalCode());
		modAddress.setCity(body.getCity());
		modAddress.setDistrict(body.getDistrict());
		modAddress.setCountry(body.getCountry());
		
		foundUser.setImage(body.getImage());
		foundUser.setName(body.getName());
		foundUser.setSurname(body.getSurname());
		foundUser.setEmail(body.getEmail());
		foundUser.setPassword(body.getPassword());
		foundUser.setAddress(modAddress);
			
		return ur.save(foundUser);
	}
	
	// --------------------------------------------------------add book to wishlist
	public User addWishlist(UUID idbook) throws NotFoundException {
			
		User currentUser = getCurrentUser();
		
		currentUser.getWishlist().add(br.findById(idbook).get());
		
		return ur.save(currentUser);
	}
	
	// --------------------------------------------------------remove book to wishlist
	public User removeWishlist(UUID idbook) throws NotFoundException {
				
		User currentUser = getCurrentUser();
			
		currentUser.getWishlist().remove(br.findById(idbook).get());
			
		return ur.save(currentUser);
	}
	
	// --------------------------------------------------------delete user by id
	public void findByIdAndDelete(UUID id) throws NotFoundException {
		User found = this.findById(id);
		ur.delete(found);
	}
	
	// --------------------------------------------------------delete current user
	public void deleteCurrentUser() throws NotFoundException {
		
		User currentUser = getCurrentUser();
		
		cs.deleteFromCurrentUser(currentUser);
		
		ur.delete(currentUser);
	}
}
