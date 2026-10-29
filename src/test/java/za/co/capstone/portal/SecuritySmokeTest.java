package za.co.capstone.portal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecuritySmokeTest {
    @Autowired MockMvc mvc;

    @Test
    void homeIsPublic() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    void adminAreaRequiresAuthentication() throws Exception {
        mvc.perform(get("/admin/users")).andExpect(status().is3xxRedirection());
    }

    @Test
    void dashboardRequiresAuthentication() throws Exception {
        mvc.perform(get("/dashboard")).andExpect(status().is3xxRedirection());
    }
}
