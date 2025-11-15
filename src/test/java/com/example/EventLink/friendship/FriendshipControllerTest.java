package com.example.EventLink.friendship;

import com.example.EventLink.friendship.dto.FriendRequestCreate;
import com.example.EventLink.friendship.dto.FriendshipDto;
import com.example.EventLink.security.CurrentUserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Web slice test for FriendshipController.
 * - Mocks CurrentUserService and FriendshipService
 * - Uses oauth2Login() to simulate an authenticated OAuth2 user
 */
@WebMvcTest(FriendshipController.class)
@ActiveProfiles("test")
class FriendshipControllerTest {

  @Autowired MockMvc mvc;

  @MockBean FriendshipService service;
  @MockBean CurrentUserService currentUser;

  @Test
  void send_requiresAuth() throws Exception {
    // When unauthenticated, your SecurityConfig currently redirects to OAuth login (302).
    // If you later enable HttpStatusEntryPoint(UNAUTHORIZED), change this to .isUnauthorized()
    mvc.perform(post("/api/friendships")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"toUserId\": 2}"))
      .andExpect(status().is3xxRedirection());
  }

  @Test
  void send_ok_whenAuthenticated() throws Exception {
    // CurrentUserService will map the OAuth principal -> app user id = 1
    when(currentUser.currentUserId(ArgumentMatchers.any())).thenReturn(1);

    // Service returns a DTO representing the new pending request
    when(service.sendRequest(eq(1), any(FriendRequestCreate.class)))
        .thenReturn(new FriendshipDto(10, 1, 2, Friendship.Status.pending, 1));

    mvc.perform(post("/api/friendships")
        .with(oauth2Login().attributes(a -> a.put("email", "alice@example.com")))
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"toUserId\": 2}"))
      .andExpect(status().isCreated())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(10))
      .andExpect(jsonPath("$.user1Id").value(1))
      .andExpect(jsonPath("$.user2Id").value(2))
      .andExpect(jsonPath("$.status").value("pending"))
      .andExpect(jsonPath("$.requestedBy").value(1));
  }
}



