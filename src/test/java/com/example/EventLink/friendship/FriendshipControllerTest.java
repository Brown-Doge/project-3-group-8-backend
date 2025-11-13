// src/test/java/com/example/EventLink/friendship/FriendshipControllerTest.java
package com.example.EventLink.friendship;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.EventLink.friendship.dto.FriendRequestCreate;
import com.example.EventLink.friendship.dto.FriendshipDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.EventLink.config.SecurityConfig;

@WebMvcTest(FriendshipController.class)
@AutoConfigureMockMvc(addFilters = true) // run with security filters
@Import(SecurityConfig.class)            // use your real SecurityConfig (401 vs 302)
@ActiveProfiles("test")
class FriendshipControllerTest {

  @Autowired MockMvc mvc;

  @MockBean FriendshipService service;

  @Test
  void send_requiresAuth() throws Exception {
    mvc.perform(post("/api/friendships")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"toUserId\": 2}"))
        .andExpect(status().isUnauthorized()); // 401, not 302
  }

  @Test
  @WithMockUser(username = "user1") // authenticated
  void send_ok_whenAuthenticated() throws Exception {
    Mockito.when(service.sendRequest(Mockito.eq(1), Mockito.any(FriendRequestCreate.class)))
           .thenReturn(new FriendshipDto(10, 1, 2, Friendship.Status.pending, 1));

    mvc.perform(post("/api/friendships?requesterId=1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"toUserId\": 2}"))
        .andExpect(status().isCreated());
  }
}


