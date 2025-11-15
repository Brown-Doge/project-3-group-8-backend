package com.example.EventLink.controller;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.EventLink.entity.UserEntity;
import com.example.EventLink.repository.UserRepository;

/**
 * Standalone MVC test for UserController (no security filters).
 * Adjust BASE to match your controller’s @RequestMapping path.
 */
class UserControllerTest {

  private static final String BASE = "/users"; // <-- change to "/api/users" if that’s your mapping

  private MockMvc mockMvc;
  private UserRepository userRepository;

  @BeforeEach
  void setup() {
    userRepository = mock(UserRepository.class);
    UserController userController = new UserController(userRepository);
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  void testGetAllUsers() throws Exception {
    // Arrange a fake user
    UserEntity testUser = new UserEntity();
    testUser.setUserId(3L);
    testUser.setUserName("testuser");
    testUser.setUserEmail("testuser@example.com");

    given(userRepository.findAll()).willReturn(Collections.singletonList(testUser));

    // Act & Assert
    mockMvc.perform(get(BASE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith("application/json"))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].userId", is(3)))
        .andExpect(jsonPath("$[0].userName", is("testuser")))
        .andExpect(jsonPath("$[0].userEmail", is("testuser@example.com")));
  }
}

