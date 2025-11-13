package com.example.EventLink.friendship;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "friendships",
    uniqueConstraints = @UniqueConstraint(name = "uk_friend_pair", columnNames = {"user1_id","user2_id"})
)
public class Friendship {

    public enum Status {
        pending,
        accepted,
        rejected
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendship_id")
    private Integer id;

    @Column(name = "user1_id", nullable = false)
    private Integer user1Id;

    @Column(name = "user2_id", nullable = false)
    private Integer user2Id;

    @Enumerated(EnumType.STRING)
    // was: @Column(name = "status", columnDefinition = "friendship_status", nullable = false)
    @Column(name = "status", nullable = false)
    private Status status = Status.pending;

    @Column(name = "requested_by", nullable = false)
    private Integer requestedBy;

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUser1Id() { return user1Id; }
    public void setUser1Id(Integer user1Id) { this.user1Id = user1Id; }

    public Integer getUser2Id() { return user2Id; }
    public void setUser2Id(Integer user2Id) { this.user2Id = user2Id; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Integer getRequestedBy() { return requestedBy; }
    public void setRequestedBy(Integer requestedBy) { this.requestedBy = requestedBy; }
}

