package xyz.softeng.shopping.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import xyz.softeng.shopping.users.user.User;
import xyz.softeng.shopping.users.user.UserMapper;
import xyz.softeng.shopping.users.user.UserRepository;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
@ActiveProfiles("test")
@WithMockUser(authorities = "SCOPE_ADMIN")
class RabbitTestConfiguration {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testListUsers() throws Exception {
        User user = new User("amir", 1000);

        given(userRepository.findAll()).willReturn(List.of(user));

        mockMvc.perform(get("/"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username").value("amir"));
    }
}
