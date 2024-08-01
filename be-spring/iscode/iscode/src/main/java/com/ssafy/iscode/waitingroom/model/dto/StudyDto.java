package com.ssafy.iscode.waitingroom.model.dto;

import com.ssafy.iscode.message.model.dto.MessageRoomUser;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "is_open", columnDefinition = "TINYINT(1)")
    private Boolean isOpen;

    @Column(name = "is_game", columnDefinition = "TINYINT(1)")
    private Boolean isGame;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User hostUser;

    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
    private List<StudyUser> users;

//    @ManyToMany
//    @JoinTable(
//            name = "study_algo",
//            joinColumns = @JoinColumn(name = "study_id"),
//            inverseJoinColumns = @JoinColumn(name = "algo_num")
//    )
//    private List<Problem> problems;
}
