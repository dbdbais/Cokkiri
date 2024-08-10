package com.ssafy.iscode.game.model.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "game_dmin",nullable = false)
    private int minDifficulty;

    @Column(name = "game_dmax",nullable = false)
    private int maxDifficulty;

    // 30 minute => 30 * 60 * 1000 => 1800000
    @Column(name = "game_time",nullable = false)
    private Long maxTime = (long) (30 * 60 * 1000);

    // 1 : item mode
    // 2 : blind mode
    // 3 : random language
    @Column(name = "game_mode",nullable = false)
    private int mode = 1;

    // user prizes => 1st, 2nd, 3rd
    // max length = 3
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GamePrize> prizes;

    public GameDto(Long id, int minDifficulty, int maxDifficulty, Long maxTime, int mode) {
        this.id = id;
        this.minDifficulty = minDifficulty;
        this.maxDifficulty = maxDifficulty;
        this.maxTime = maxTime;
        this.mode = mode;
        this.prizes = new ArrayList<>();
    }
}
