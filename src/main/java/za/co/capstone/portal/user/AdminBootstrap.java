package za.co.capstone.portal.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Creates an administrator only when explicit environment variables are provided.
 * Never place real credentials in source control.
 */
@Configuration
public class AdminBootstrap {
    @Bean
    CommandLineRunner createInitialAdmin(UserRepository users, PasswordEncoder encoder,
            @Value("${PORTAL_ADMIN_USERNAME:}") String username,
            @Value("${PORTAL_ADMIN_PASSWORD:}") String password) {
        return args -> {
            if (username.isBlank() || password.isBlank()) {
                System.out.println("Admin bootstrap skipped: set PORTAL_ADMIN_USERNAME and PORTAL_ADMIN_PASSWORD to create an admin.");
                return;
            }
            if (password.length() < 12) {
                throw new IllegalStateException("PORTAL_ADMIN_PASSWORD must be at least 12 characters.");
            }
            if (!users.existsByUsername(username)) {
                users.save(new UserAccount(username, encoder.encode(password), Role.ADMIN));
                System.out.println("Initial administrator account created from environment configuration.");
            }
        };
    }
}
