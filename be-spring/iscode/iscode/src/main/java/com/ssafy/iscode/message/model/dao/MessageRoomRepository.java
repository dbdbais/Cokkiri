package com.ssafy.iscode.message.model.dao;

import com.ssafy.iscode.message.model.dto.MessageDto;
import com.ssafy.iscode.message.model.dto.MessageRoomDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRoomRepository extends JpaRepository<MessageRoomDto, Long> {

    // get room id with both users
    @Query("SELECT mr.id FROM MessageRoom mr JOIN mr.users u1 JOIN mr.users u2 WHERE u1.id = :userId1 AND u2.id = :userId2")
    Optional<Long> findMessageRoomIdsByUserIds(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
}
