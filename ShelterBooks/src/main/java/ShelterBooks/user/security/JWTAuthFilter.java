package ShelterBooks.user.security;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import ShelterBooks.user.User;
import ShelterBooks.user.UserService;
import ShelterBooks.user.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter{

	@Autowired
	JWTTools jwttools;
	
	@Autowired
	UserService us;
	
	private static final String[] PUBLIC_ROUTES = { 
			"/books", // GET
			"/books/filter/**",
			"/carts/currentUser",
			"/carts/addBook/*",
			"/carts/removeBook/*",
			"/carts/clearCart",
			"/users/addWishlist/*",
			"/users/removeWishlist/*",
			"/users/updateCurrent",
			"/users/deleteCurrent"
	};
	
	private static final String[] ADMIN_ROUTES = { 
			"/books", // POST
			"/books/updateCopies/**",
			"/users",
			"/users/idUser/*",
			"/users/email/*",
			"/users/*",
			"/books/filter/**",
	};

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		String servletPath = request.getServletPath();
		
		if (isPublicRoute(servletPath) && (request.getMethod().equals("GET")
				|| (request.getMethod().equals("POST") && "/reviews".equals(servletPath)))) {
			filterChain.doFilter(request, response);
			return;
		}
		
		if (authHeader == null || !authHeader.startsWith("Bearer "))
			throw new UnauthorizedException("Per favore passa il token nell'authorization header");
		
		String token = authHeader.substring(7);
		
		try {
			
			System.out.println("TOKEN -------> " + token);

			jwttools.verifyToken(token);

			String id = jwttools.extractSubject(token);
		
			User currentUser = us.findById(UUID.fromString(id));

			if (isAdminRoute(servletPath) && !"ADMIN".equalsIgnoreCase(currentUser.getRole().toString())) {
				throw new UnauthorizedException("L'accesso a questa route richiede il ruolo di admin");
			}
			
			UsernamePasswordAuthenticationToken authToken = 
					new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authToken);

			filterChain.doFilter(request, response);
			
		} catch (Exception e) {
		}
		

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		System.out.println(request.getServletPath());
		return new AntPathMatcher().match("/auth/**", request.getServletPath());
	}
	
	// Verifica se la route è pubblica per le richieste GET
	private boolean isPublicRoute(String servletPath) {
		for (String publicRoute : PUBLIC_ROUTES) {
			if (new AntPathMatcher().match(publicRoute, servletPath)) {
				return true;
			}
		}
		return false;
	}

	// Verifica se la route richiede il ruolo di admin
	private boolean isAdminRoute(String servletPath) {
		for (String adminRoute : ADMIN_ROUTES) {
			if (new AntPathMatcher().match(adminRoute, servletPath)) {
				return true;
			}
		}
		return false;
	}
	
}
