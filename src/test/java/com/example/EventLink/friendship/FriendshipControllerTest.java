package com.example.EventLink.friendship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.example.EventLink.friendship.dto.FriendRequestCreate;
import com.example.EventLink.friendship.dto.FriendshipDto;
import com.example.EventLink.security.CurrentUserService;

class FriendshipControllerTest {

  private MockMvc mvc;
  private FriendshipService service;
  private CurrentUserService currentUser;

  // Resolves @AuthenticationPrincipal to a dummy OAuth2User for standalone MockMvc
  static class DummyPrincipalResolver implements HandlerMethodArgumentResolver {
    private final OAuth2User principal;
    DummyPrincipalResolver(OAuth2User principal) { this.principal = principal; }
    @Override public boolean supportsParameter(MethodParameter p) {
      return p.hasParameterAnnotation(AuthenticationPrincipal.class)
             && OAuth2User.class.isAssignableFrom(p.getParameterType());
    }
    @Override public Object resolveArgument(MethodParameter p, ModelAndViewContainer m,
                                            NativeWebRequest w, WebDataBinderFactory b) {
      return principal;
    }
  }

  @BeforeEach
  void setup() {
    service = mock(FriendshipService.class);
    currentUser = mock(CurrentUserService.class);

    FriendshipController controller = new FriendshipController(service, currentUser);

    OAuth2User fakePrincipal = mock(OAuth2User.class);
    mvc = MockMvcBuilders.standaloneSetup(controller)
         .setCustomArgumentResolvers(new DummyPrincipalResolver(fakePrincipal))
         .build();
  }

  @Test
  void send_ok_whenAuthenticated() throws Exception {
    when(currentUser.currentUserId(any(OAuth2User.class))).thenReturn(1);
    when(service.sendRequest(eq(1), any(FriendRequestCreate.class)))
        .thenReturn(new FriendshipDto(10, 1, 2, Friendship.Status.pending, 1));

    mvc.perform(post("/api/friendships")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"toUserId\": 2}"))
      .andExpect(status().isCreated());
  }
}






