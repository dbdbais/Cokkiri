package com.ssafy.iscode.message.model.dto;

import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "message_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRoomDto {

    @Id
    @Column(name = "mr_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // user list (maximum: 2)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "message_room_user",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;
}
