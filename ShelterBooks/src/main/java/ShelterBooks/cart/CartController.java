package ShelterBooks.cart;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {

	@Autowired
	CartService cartService;
	
	// --------------------------------------------------------find cart by user id
	@GetMapping("/{idUser}")
	public Cart findById(@PathVariable UUID idUser) {
		return cartService.findCartByUserId(idUser);		
	}
	
}
