package com.example.EventLink.friendship.dto;

import jakarta.validation.constraints.NotNull;

public record FriendRequestDecision(@NotNull String action) { } // "accept" | "reject"
