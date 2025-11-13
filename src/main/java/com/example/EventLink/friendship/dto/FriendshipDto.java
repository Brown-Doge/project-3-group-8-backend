package com.example.EventLink.friendship.dto;

import com.example.EventLink.friendship.Friendship.Status;

public record FriendshipDto(
  Integer id, Integer user1Id, Integer user2Id, Status status, Integer requestedBy
) {}

