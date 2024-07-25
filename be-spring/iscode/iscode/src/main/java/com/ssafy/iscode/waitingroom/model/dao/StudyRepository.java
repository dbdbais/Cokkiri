package com.ssafy.iscode.waitingroom.model.dao;

import com.ssafy.iscode.waitingroom.model.dto.StudyDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRepository extends JpaRepository<StudyDto, Long> {
}
