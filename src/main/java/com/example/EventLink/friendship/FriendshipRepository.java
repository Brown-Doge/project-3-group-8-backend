package com.example.EventLink.friendship;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

  @Query("""
    select f from Friendship f
    where (f.user1Id = :a and f.user2Id = :b)
       or (f.user1Id = :b and f.user2Id = :a)
  """)
  Optional<Friendship> findBetween(Integer a, Integer b);

  // derived query uses the *property* name: user2Id
  List<Friendship> findAllByUser2IdAndStatus(Integer userId, Friendship.Status status);

  @Query("""
    select f from Friendship f
    where f.user1Id = :userId or f.user2Id = :userId
  """)
  List<Friendship> findAllForUser(Integer userId);
}

