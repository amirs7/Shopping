package xyz.softeng.shopping.rating;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import xyz.softeng.shopping.rating.rating.PurchaseRating;
import xyz.softeng.shopping.rating.rating.RatingRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ActiveProfiles("test")
@WithMockUser(username = "amir")
class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingRepository repository;

    @Test
    void testRate() throws Exception {
        PurchaseRating purchaseRating = new PurchaseRating();

        given(repository.findByProductIdAndUsername(10L, "amir"))
                .willReturn(Optional.of(purchaseRating));

        mockMvc.perform(post("/")
                .param("productId", "10")
                .param("value", "5")
                .with(csrf()))
                .andExpect(status().isOk());

        var captor = ArgumentCaptor.forClass(PurchaseRating.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getValue()).isEqualTo(5);
        assertThat(captor.getValue().isRated()).isTrue();
    }
}
