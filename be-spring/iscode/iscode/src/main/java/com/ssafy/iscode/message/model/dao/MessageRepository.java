package com.ssafy.iscode.message.model.dao;

import com.ssafy.iscode.message.model.dto.MessageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageDto, Long> {

    // find all messages by room id
    @Query("SELECT m FROM MessageDto m WHERE m.messageRoom.id = :roomId")
    List<MessageDto> findAllByRoomId(@Param("roomId") Long roomId);
}
