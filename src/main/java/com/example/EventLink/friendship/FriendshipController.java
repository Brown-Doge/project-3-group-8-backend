package com.example.EventLink.friendship;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.EventLink.friendship.dto.FriendRequestCreate;
import com.example.EventLink.friendship.dto.FriendRequestDecision;
import com.example.EventLink.friendship.dto.FriendshipDto;
import com.example.EventLink.security.CurrentUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/friendships")
@Validated
public class FriendshipController {

    private final FriendshipService service;
    private final CurrentUserService currentUser;

    public FriendshipController(FriendshipService service, CurrentUserService currentUser) {
        this.service = service;
        this.currentUser = currentUser;
    }

    /** Send a friend request to body.toUserId on behalf of the logged-in user. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FriendshipDto send(
            @AuthenticationPrincipal OAuth2User principal,
            @Valid @RequestBody FriendRequestCreate body) {

        Integer requesterId = currentUser.currentUserId(principal);
        return service.sendRequest(requesterId, body);
    }

    /** Accept or reject a request (PUT is fine too). Only the recipient may decide. */
    
  @PutMapping("/{id}") // was PATCH – PUT is fine for “decide”
  public FriendshipDto decide(@AuthenticationPrincipal OAuth2User principal,
                              @PathVariable("id") Integer friendshipId,
                              @Valid @RequestBody FriendRequestDecision body) {
    Integer actingUserId = currentUser.currentUserId(principal);
    return service.decide(actingUserId, friendshipId, body);
  }

    /** All relations involving the logged-in user. */
    @GetMapping
    public List<FriendshipDto> list(@AuthenticationPrincipal OAuth2User principal) {
        Integer userId = currentUser.currentUserId(principal);
        return service.listForUser(userId);
    }

    /** Pending requests where the logged-in user is the recipient. */
    @GetMapping("/pending")
    public List<FriendshipDto> pending(@AuthenticationPrincipal OAuth2User principal) {
        Integer userId = currentUser.currentUserId(principal);
        return service.pendingForUser(userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("id") Integer friendshipId,
            @AuthenticationPrincipal OAuth2User principal) {
        Integer userId = currentUser.currentUserId(principal);
        service.delete(friendshipId, userId);
    }
}


