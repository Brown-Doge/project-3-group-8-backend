package com.example.EventLink.controller;

import com.example.EventLink.entity.UserEntity;
import com.example.EventLink.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        // Mock the repository
        userRepository = mock(UserRepository.class);

        // Create controller with mocked repository
        UserController userController = new UserController(userRepository);

        // Build MockMvc for standalone testing
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testGetAllUsers() throws Exception {
        // Setup test user
        UserEntity testUser = new UserEntity();
        testUser.setUserId(3L);
        testUser.setUserName("testuser");
        testUser.setUserEmail("testuser@example.com");

        // Mock repository call
        given(userRepository.findAll()).willReturn(Collections.singletonList(testUser));

        // Perform GET request and assert
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId", is(3)))
                .andExpect(jsonPath("$[0].userName", is("testuser")))
                .andExpect(jsonPath("$[0].userEmail", is("testuser@example.com")));
    }
}
