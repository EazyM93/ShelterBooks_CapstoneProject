package ShelterBooks.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/carts")
public class CartController {

	@Autowired
	CartRepository cartRepository;
	
	
	
}
