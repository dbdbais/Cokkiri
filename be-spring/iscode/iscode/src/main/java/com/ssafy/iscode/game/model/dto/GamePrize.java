package com.ssafy.iscode.game.model.dto;

import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "game_prize")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Check(constraints = "ranking BETWEEN 1 AND 3")
public class GamePrize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private GameDto game;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "ranking")
    private int ranking; // rank

    @Column(name = "time")
    private long time;

    @Override
    public String toString() {
        return "GamePrize{" +
                "id=" + id +
                ", game=" + game +
                ", user=" + user +
                ", rank=" + ranking +
                ", time=" + time +
                '}';
    }
}