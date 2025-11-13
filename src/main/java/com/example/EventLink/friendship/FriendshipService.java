package com.example.EventLink.friendship;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EventLink.friendship.dto.FriendRequestCreate;
import com.example.EventLink.friendship.dto.FriendRequestDecision;
import com.example.EventLink.friendship.dto.FriendshipDto;

@Service
public class FriendshipService {

  private final FriendshipRepository repo;

  public FriendshipService(FriendshipRepository repo) {
    this.repo = repo;
  }

  @Transactional
  public FriendshipDto sendRequest(Integer requesterId, FriendRequestCreate body) {
    Integer toUserId = body.toUserId();
    if (requesterId.equals(toUserId)) {
      throw new IllegalArgumentException("You cannot friend yourself.");
    }

    var existing = repo.findBetween(requesterId, toUserId);
    if (existing.isPresent()) {
      return toDto(existing.get());
    }

    Friendship f = new Friendship();
    f.setUser1Id(requesterId);
    f.setUser2Id(toUserId);
    f.setRequestedBy(requesterId);
    f.setStatus(Friendship.Status.pending);

    return toDto(repo.save(f));
  }

  @Transactional
  public FriendshipDto decide(Integer actingUserId, Integer friendshipId, FriendRequestDecision decision) {
    var f = repo.findById(friendshipId)
        .orElseThrow(() -> new IllegalArgumentException("Friendship not found"));

    if (!actingUserId.equals(f.getUser2Id())) {
      throw new IllegalArgumentException("Only the recipient can accept/reject.");
    }

    String action = decision.action().toLowerCase();
    switch (action) {
      case "accept" -> f.setStatus(Friendship.Status.accepted);
      case "reject" -> f.setStatus(Friendship.Status.rejected);
      default -> throw new IllegalArgumentException("action must be 'accept' or 'reject'");
    }

    return toDto(repo.save(f));
  }

  @Transactional(readOnly = true)
  public List<FriendshipDto> listForUser(Integer userId) {
    return repo.findAllForUser(userId).stream().map(this::toDto).toList();
  }

  @Transactional(readOnly = true)
  public List<FriendshipDto> pendingForUser(Integer userId) {
    return repo.findAllByUser2IdAndStatus(userId, Friendship.Status.pending)
               .stream().map(this::toDto).toList();
  }

  private FriendshipDto toDto(Friendship f) {
    return new FriendshipDto(
        f.getId(),
        f.getUser1Id(),
        f.getUser2Id(),
        f.getStatus(),
        f.getRequestedBy()
    );
  }
}
