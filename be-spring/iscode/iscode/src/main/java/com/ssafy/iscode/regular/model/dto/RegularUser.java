package com.ssafy.iscode.regular.model.dto;

import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "regular_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegularUser {
    @Id
    @Column(name = "ru_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regular_id")
    private RegularDto regular;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_accept", columnDefinition = "TINYINT(1)")
    private Boolean isAccept;
}
