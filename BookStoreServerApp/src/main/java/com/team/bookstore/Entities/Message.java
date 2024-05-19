package com.team.bookstore.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message extends Auditable{
    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String message_content;
    int message_status;
    @JsonBackReference("sender")
    @ManyToOne
    @JoinColumn(name = "sender_id")
    User sender;
    @JsonBackReference("receiver")
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    User receiver;
}