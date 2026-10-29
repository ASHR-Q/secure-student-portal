package za.co.capstone.portal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import za.co.capstone.portal.user.Role;
import za.co.capstone.portal.user.UserAccount;
import za.co.capstone.portal.user.UserRepository;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:security-flow-tests;DB_CLOSE_DELAY=-1")
@AutoConfigureMockMvc
class SecurityFlowTest {
    private static final String STUDENT_USERNAME = "teststudent";
    private static final String STUDENT_PASSWORD = "student-password-123";
    private static final String ADMIN_USERNAME = "testadmin";
    private static final String ADMIN_PASSWORD = "admin-password-123";

    @Autowired MockMvc mvc;
    @Autowired UserRepository users;
    @Autowired PasswordEncoder passwordEncoder;

    @BeforeEach
    void prepareAccounts() {
        users.deleteAll();
        users.save(new UserAccount(
                STUDENT_USERNAME, passwordEncoder.encode(STUDENT_PASSWORD), Role.STUDENT));
        users.save(new UserAccount(
                ADMIN_USERNAME, passwordEncoder.encode(ADMIN_PASSWORD), Role.ADMIN));
    }

    @Test
    void registrationCreatesStudentWithEncodedPasswordEvenIfAdminRoleIsSubmitted() throws Exception {
        mvc.perform(post("/register")
                        .with(csrf())
                        .param("username", "newstudent")
                        .param("password", "new-student-password")
                        .param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registered"));

        UserAccount registered = users.findByUsername("newstudent").orElseThrow();
        Assertions.assertEquals(Role.STUDENT, registered.getRole());
        Assertions.assertNotEquals("new-student-password", registered.getPasswordHash());
        Assertions.assertTrue(
                passwordEncoder.matches("new-student-password", registered.getPasswordHash()));
    }

    @Test
    void registrationRejectsShortPasswordsAndInvalidUsernamesServerSide() throws Exception {
        mvc.perform(post("/register")
                        .with(csrf())
                        .param("username", "shortpass")
                        .param("password", "short"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("form", "password"));

        mvc.perform(post("/register")
                        .with(csrf())
                        .param("username", "bad username")
                        .param("password", "long-enough-password"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("form", "username"));

        Assertions.assertFalse(users.existsByUsername("shortpass"));
    }

    @Test
    void registrationRequiresCsrfToken() throws Exception {
        mvc.perform(post("/register")
                        .param("username", "csrfstudent")
                        .param("password", "csrf-student-password"))
                .andExpect(status().isForbidden());

        Assertions.assertFalse(users.existsByUsername("csrfstudent"));
    }

    @Test
    void validLoginAuthenticatesAndInvalidLoginDoesNot() throws Exception {
        mvc.perform(formLogin().user(STUDENT_USERNAME).password(STUDENT_PASSWORD))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"))
                .andExpect(authenticated().withUsername(STUDENT_USERNAME));

        mvc.perform(formLogin().user(STUDENT_USERNAME).password("incorrect-password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }

    @Test
    void studentCannotAccessAdminUsersButAdminCan() throws Exception {
        mvc.perform(get("/admin/users").with(user(STUDENT_USERNAME).roles("STUDENT")))
                .andExpect(status().isForbidden());

        mvc.perform(get("/admin/users").with(user(ADMIN_USERNAME).roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(ADMIN_USERNAME)));
    }
}
