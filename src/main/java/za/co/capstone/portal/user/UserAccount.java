package za.co.capstone.portal.user;

import jakarta.persistence.*;

@Entity
@Table(name = "user_accounts", uniqueConstraints = @UniqueConstraint(name = "uk_user_username", columnNames = "username"))
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String username;

    @Column(nullable = false, length = 100)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.STUDENT;

    protected UserAccount() {}

    public UserAccount(String username, String passwordHash, Role role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setRole(Role role) { this.role = role; }
}
