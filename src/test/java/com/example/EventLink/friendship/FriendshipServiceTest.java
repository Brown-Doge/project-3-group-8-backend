// src/test/java/com/example/EventLink/friendship/FriendshipServiceTest.java
package com.example.EventLink.friendship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static com.example.EventLink.friendship.Friendship.Status.pending;
import com.example.EventLink.friendship.dto.FriendRequestCreate;
import com.example.EventLink.friendship.dto.FriendRequestDecision;
import com.example.EventLink.friendship.dto.FriendshipDto;

@DataJpaTest
@Import(FriendshipService.class)
@ActiveProfiles("test")
class FriendshipServiceTest {

  @Autowired FriendshipService service;
  @Autowired FriendshipRepository repo;

  @Test
  void cannotFriendSelf() {
    assertThatThrownBy(() ->
      service.sendRequest(1, new FriendRequestCreate(1))
    ).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void send_createsPending_andRequestedBy() {
    FriendshipDto dto = service.sendRequest(1, new FriendRequestCreate(2));
    assertThat(dto.user1Id()).isEqualTo(1);
    assertThat(dto.user2Id()).isEqualTo(2);
    assertThat(dto.requestedBy()).isEqualTo(1);
    assertThat(dto.status()).isEqualTo(pending);
  }

  @Test
  void send_isIdempotent_ifExists() {
    service.sendRequest(1, new FriendRequestCreate(2));
    FriendshipDto again = service.sendRequest(1, new FriendRequestCreate(2));
    assertThat(again.status()).isEqualTo(pending);
    assertThat(repo.findAll()).hasSize(1);
  }

  @Test
  void onlyRecipientCanDecide() {
    FriendshipDto dto = service.sendRequest(1, new FriendRequestCreate(2));

    // requester (1) is NOT recipient (2) -> should throw
    assertThatThrownBy(() ->
      service.decide(1, dto.id(), new FriendRequestDecision("accept"))
    ).isInstanceOf(IllegalArgumentException.class);

    // recipient (2) can accept
    FriendshipDto afterDecision =
      service.decide(2, dto.id(), new FriendRequestDecision("accept"));
    assertThat(afterDecision.status()).isEqualTo(Friendship.Status.accepted);
  }
}

