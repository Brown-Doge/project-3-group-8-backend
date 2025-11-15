// src/test/java/com/example/EventLink/friendship/FriendshipControllerTest.java
package com.example.EventLink.friendship;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.EventLink.friendship.dto.FriendRequestCreate;
import com.example.EventLink.friendship.dto.FriendshipDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class FriendshipControllerTest {

  private MockMvc mockMvc;
  private FriendshipService friendshipService;

  @BeforeEach
  void setup() {
    // Mock the service
    friendshipService = Mockito.mock(FriendshipService.class);

    // Create controller with mocked service
    FriendshipController friendshipController = new FriendshipController(friendshipService);

    // Build MockMvc for standalone testing (no Spring context)
    mockMvc = MockMvcBuilders.standaloneSetup(friendshipController).build();
  }

  @Test
  void send_requiresAuth() throws Exception {
    // Mock the service to return a friendship
    Mockito.when(friendshipService.sendRequest(Mockito.anyInt(), Mockito.any(FriendRequestCreate.class)))
           .thenReturn(new FriendshipDto(10, 1, 2, Friendship.Status.pending, 1));
           
    mockMvc.perform(post("/api/friendships?requesterId=1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"toUserId\": 2}"))
        .andExpect(status().isCreated()); // Should succeed since no auth required in standalone test
  }

  @Test
  void send_ok_whenAuthenticated() throws Exception {
    Mockito.when(friendshipService.sendRequest(Mockito.eq(1), Mockito.any(FriendRequestCreate.class)))
           .thenReturn(new FriendshipDto(10, 1, 2, Friendship.Status.pending, 1));

    mockMvc.perform(post("/api/friendships?requesterId=1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"toUserId\": 2}"))
        .andExpect(status().isCreated());
  }
}


