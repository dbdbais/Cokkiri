package com.ssafy.iscode.waitingroom.model.dto;

import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.annotations.One;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "study")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyDto {
    @Id
    @Column(name = "study_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "study_name", length = 50)
    private String name;

    @Column(name = "study_comment", length = 900)
    private String comment;

    @Column(name = "study_rule", length = 900)
    private String rule;

    @Column(name = "study_start")
    @CreationTimestamp
    private Date start;

    @Column(name = "study_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @Column(name = "is_open")
    private boolean isOpen;

    @Column(name = "is_game")
    private boolean isGame;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User hostUser;

    @ManyToMany
    @JoinTable(
            name = "study_user",
            joinColumns = @JoinColumn(name = "study_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

//    @ManyToMany
//    @JoinTable(
//            name = "study_algo",
//            joinColumns = @JoinColumn(name = "study_id"),
//            inverseJoinColumns = @JoinColumn(name = "algo_num")
//    )
//    private List<Problem> problems;
}
