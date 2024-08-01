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
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<MessageRoomUser> users;
}
