package za.co.capstone.portal.web;

import jakarta.validation.Valid;
import za.co.capstone.portal.user.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {
    private final UserService userService;
    private final UserRepository users;

    public PageController(UserService userService, UserRepository users) {
        this.userService = userService;
        this.users = users;
    }

    @GetMapping("/")
    public String home() { return "home"; }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("form", new RegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") RegistrationForm form,
                           BindingResult result, Model model) {
        if (result.hasErrors()) return "register";
        try {
            userService.registerStudent(form);
        } catch (UserService.DuplicateUsernameException ex) {
            model.addAttribute("registrationError", "That username is unavailable.");
            return "register";
        }
        return "redirect:/login?registered";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        UserAccount user = users.findByUsername(authentication.getName()).orElseThrow();
        model.addAttribute("user", user);
        return "dashboard";
    }

    @GetMapping("/admin/users")
    public String adminUsers(Model model) {
        model.addAttribute("users", users.findAll());
        return "admin-users";
    }
}
