package za.co.capstone.portal.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationForm {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 40, message = "Username must be 3–40 characters")
    @Pattern(regexp = "^[A-Za-z0-9_.-]+$", message = "Use letters, numbers, dots, underscores or hyphens only")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 12, max = 72, message = "Password must be 12–72 characters")
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
