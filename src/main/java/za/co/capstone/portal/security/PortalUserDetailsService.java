package za.co.capstone.portal.security;

import za.co.capstone.portal.user.UserAccount;
import za.co.capstone.portal.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortalUserDetailsService implements UserDetailsService {
    private final UserRepository users;

    public PortalUserDetailsService(UserRepository users) { this.users = users; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = users.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
    }
}
