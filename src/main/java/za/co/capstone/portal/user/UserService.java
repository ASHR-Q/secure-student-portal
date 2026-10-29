package za.co.capstone.portal.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository users;
    private final PasswordEncoder encoder;

    public UserService(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @Transactional
    public void registerStudent(RegistrationForm form) {
        String username = form.getUsername().trim();
        if (users.existsByUsername(username)) {
            throw new DuplicateUsernameException();
        }
        users.save(new UserAccount(username, encoder.encode(form.getPassword()), Role.STUDENT));
    }

    public static class DuplicateUsernameException extends RuntimeException {}
}
