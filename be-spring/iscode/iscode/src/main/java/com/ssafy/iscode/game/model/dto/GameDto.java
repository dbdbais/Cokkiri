package com.ssafy.iscode.game.model.dto;

import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

// additional room setting
@Entity
@Table(name = "game")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {
    // game id == waiting room's id
    // => not auto increment
    @Id
    @Column(name = "game_id")
    private Long id;

    // level 3 ~ 5 => 3|5
    @Column(name = "game_diff")
    private String difficulty;

    // 30 minute => 30 * 60 * 1000 => 3600000
    @Column(name = "game_time")
    private Long maxTime;

    // user success times
    // milli-second
    // 123113, 1123331, 2973314
    // => 123113|1123331|2973314
    @Column(name = "user_times")
    private String times;

    // 1 : item mode
    // 2 : blind mode
    // 3 : random language
    @Column(name = "game_mode")
    private int mode;

    // user prices => 1st, 2nd, 3rd
    // max length = 3
    private List<User> prices;

    // selected problems
    private List<Problem> problems;
}
