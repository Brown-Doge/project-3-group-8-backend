package com.example.EventLink.friendship;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.EventLink.friendship.dto.FriendRequestCreate;
import com.example.EventLink.friendship.dto.FriendRequestDecision;
import com.example.EventLink.friendship.dto.FriendshipDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/friendships")
@Validated
public class FriendshipController {

  private final FriendshipService service;

  public FriendshipController(FriendshipService service) {
    this.service = service;
  }

  // For now, take requester/acting user from query to keep it simple.
  // Later you can swap to the authenticated user id from SecurityContext.
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public FriendshipDto send(
      @RequestParam("requesterId") Integer requesterId,
      @Valid @RequestBody FriendRequestCreate body) {
    return service.sendRequest(requesterId, body);
  }

  @PatchMapping("/{id}")
  public FriendshipDto decide(
      @PathVariable("id") Integer friendshipId,
      @RequestParam("actingUserId") Integer actingUserId,
      @Valid @RequestBody FriendRequestDecision body) {
    return service.decide(actingUserId, friendshipId, body);
  }

  @GetMapping
  public List<FriendshipDto> list(@RequestParam("userId") Integer userId) {
    return service.listForUser(userId);
  }

  @GetMapping("/pending")
  public List<FriendshipDto> pending(@RequestParam("userId") Integer userId) {
    return service.pendingForUser(userId);
  }
}

