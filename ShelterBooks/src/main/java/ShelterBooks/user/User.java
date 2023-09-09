package ShelterBooks.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ShelterBooks.book.Book;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="users")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class User implements UserDetails{

	@Id
	@GeneratedValue
	private UUID idUser;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String surname;
	
	@Column(nullable = false, unique = true)	
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	// lista libri acquistati
	@OneToMany
	@JoinTable(name = "userPurchasedBooks")
	@Builder.Default
	private List<Book> purchasedBooks = new ArrayList<>();
	
	// lista libri acquistati
	@OneToMany
	@JoinTable(name = "userWishlist")
	@Builder.Default
	private List<Book> wishlist = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 if (this.role != null) {
			 return List.of(new SimpleGrantedAuthority(role.name()));
		 }
		 
		 return Collections.emptyList();
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
