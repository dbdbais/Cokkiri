package com.ssafy.iscode.problem.model.dao;

import com.ssafy.iscode.problem.model.dto.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem,Long> {

}
