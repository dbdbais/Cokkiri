package com.ssafy.iscode.message.model.dto;

import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mru")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRoomUser {

    @Id
    @Column(name = "mru_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mr_id")
    private MessageRoomDto room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
