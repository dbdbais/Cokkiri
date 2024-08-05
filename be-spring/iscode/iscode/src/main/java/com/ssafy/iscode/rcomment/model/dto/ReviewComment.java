package com.ssafy.iscode.rcomment.model.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "rComment")
public class ReviewComment {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cid;

}
