package com.ssafy.iscode.message.model.dao;

import com.ssafy.iscode.message.model.dto.MessageDto;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepository {

    private EntityManager em;

    public MessageRepository (EntityManager em) {
        this.em = em;
    }

    @Transactional
    public int save (MessageDto messageDto) {
        try {
            em.persist(messageDto);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // find all messages by room id
    public List<MessageDto> findAllByRoomId(Long roomId) {
        String query = "SELECT m FROM MessageDto m"
            + "WHERE m.messageRoom.id = :roomId";
        return em.createQuery(query, MessageDto.class)
                .setParameter("roomId", roomId)
                .getResultList();
    }
}
