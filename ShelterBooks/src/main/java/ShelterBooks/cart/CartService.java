package ShelterBooks.cart;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ShelterBooks.user.User;

@Service
public class CartService {

	@Autowired
	CartRepository cartRepository;
	
	//-------------------------------------------------------------------------save cart
	public Cart saveCart(User user){
		
		Cart newCart = Cart.builder()
				.user(user)
				.books(new ArrayList<>())
				.build();
		
		return cartRepository.save(newCart);
	}
	
}
