package com.example.EventLink.friendship.dto;

import jakarta.validation.constraints.NotNull;

public record FriendRequestCreate(@NotNull Integer toUserId) {}

