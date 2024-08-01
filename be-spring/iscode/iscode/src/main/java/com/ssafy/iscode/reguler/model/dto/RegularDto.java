package com.ssafy.iscode.reguler.model.dto;

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
@Table(name = "regular")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegularDto {
    @Id
    @Column(name = "regular_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "regular_name")
    private String name;

    @Column(name = "regular_comment")
    private String comment;

    @Column(name = "regular_rule")
    private String rules;

    @Column(name = "regular_lang")
    private String language;

    @Column(name = "regular_max")
    private int maxNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User hostUser;

    @Column(name = "regular_start")
    @CreationTimestamp
    private Date start;

    @Column(name = "regular_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @OneToMany(mappedBy = "regular", fetch = FetchType.LAZY)
    private List<RegularTimeDto> times;

    @OneToMany(mappedBy = "regular", fetch = FetchType.LAZY)
    private List<RegularUser> users;
}
