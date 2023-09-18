package ShelterBooks;

import java.io.FileInputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ShelterBooks.user.User;
import ShelterBooks.user.UserRepository;
import ShelterBooks.user.UserRole;

@Component
public class AdminRunner implements CommandLineRunner{

	@Autowired
	UserRepository ur;
	
	@Override
	public void run(String... args) throws Exception {
		
		// fetching env.properties file
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream("env.properties");
		properties.load(fileInputStream);
		fileInputStream.close();
		
		// search for an admin in the database
	    boolean adminMatch = false;
	    
		for(User u : ur.findAll()) {
			if(u.getRole().equals(UserRole.ADMIN))
				adminMatch = true;
		}
		
		// if no admin is found, it creates one
		if(!adminMatch) {
			
			User defaultAdmin = User.builder()
				.image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS5j1QoysD2S9Mq32jDCO9ExkcKWP19RbcDxA&usqp=CAU")
				.name("admin")
				.surname("admin")
				.email("admin@admin.com")
				.password(properties.getProperty("ps_defaultAdmin"))
				.role(UserRole.ADMIN)
				.build();
		
			ur.save(defaultAdmin);
			
		}
		
		
	}

}
