package com.ssafy.iscode.game.model.dto;

import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.util.List;
import java.util.Map;

// additional room setting
@Entity
@Table(name = "game")
@Getter
@Setter
@NoArgsConstructor
@Check(constraints = "game_diff BETWEEN 1 AND 5 AND game_mode BETWEEN 1 AND 3")
public class GameDto {
    // game id == waiting room's id
    // => not auto increment
    @Id
    @Column(name = "game_id")
    private Long id;

    // level 3 ~ 5 => 3|5
    @Column(name = "game_diff",nullable = false)
    private int difficulty;

    // 30 minute => 30 * 60 * 1000 => 3600000
    @Column(name = "game_time",nullable = false)
    private Long maxTime;

    // 1 : item mode
    // 2 : blind mode
    // 3 : random language
    @Column(name = "game_mode",nullable = false)
    private int mode;

    // user prizes => 1st, 2nd, 3rd
    // max length = 3
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GamePrize> prizes;

    public GameDto(Long id, int difficulty, Long maxTime, int mode) {
        this.id = id;
        this.difficulty = difficulty;
        this.maxTime = maxTime;
        this.mode = mode;
    }
}
