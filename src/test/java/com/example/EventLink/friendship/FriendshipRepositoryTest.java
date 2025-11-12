// src/test/java/com/example/EventLink/friendship/FriendshipRepositoryTest.java
package com.example.EventLink.friendship;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class FriendshipRepositoryTest {

  @Autowired FriendshipRepository repo;

  @Test
  void findBetween_findsRegardlessOfOrder() {
    // arrange
    Friendship f = new Friendship();
    f.setUser1Id(1);
    f.setUser2Id(2);
    f.setRequestedBy(1);
    f.setStatus(Friendship.Status.pending);
    repo.saveAndFlush(f);

    // act
    Optional<Friendship> aThenB = repo.findBetween(1, 2);
    Optional<Friendship> bThenA = repo.findBetween(2, 1);

    // assert
    assertThat(aThenB).isPresent();
    assertThat(bThenA).isPresent();
    assertThat(aThenB.get().getId()).isEqualTo(bThenA.get().getId());
  }
}
