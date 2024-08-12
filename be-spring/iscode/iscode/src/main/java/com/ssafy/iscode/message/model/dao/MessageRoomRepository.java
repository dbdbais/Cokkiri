package com.ssafy.iscode.message.model.dao;

import com.ssafy.iscode.message.model.dto.MessageRoomDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MessageRoomRepository {

    private final EntityManager em;

    public MessageRoomRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public MessageRoomDto save(MessageRoomDto messageRoomDto) {
        try {

            if(messageRoomDto.getId() == null){
                //Insert
                em.persist(messageRoomDto);
            }
            else{
                //Modify
                em.merge(messageRoomDto);
            }
            return messageRoomDto; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();;
            return null; // failed
        }
    }

    public MessageRoomDto findById(Long id){
        return em.find(MessageRoomDto.class, id);
    }

    public int remove(Long id){

        MessageRoomDto delRoom = findById(id);
        if(delRoom != null){
            em.remove(delRoom);
            return 1;
            // successfully deleted
        }
        else{
            return 0;
            // failed
        }
    }

    // find id of room
    // both users in room => roomId
    // else => null
    public Optional<Long> findRoom(String user1, String user2){
        String query = "SELECT mr.id FROM MessageRoom mr"
            + "JOIN mr.users u1 JOIN mr.users u2"
            + "WHERE u1.id = :user1 AND u2.id = :user2";
        try {
            List<Long> roomId = em.createQuery(query, Long.class)
                    .setParameter("user1", user1)
                    .setParameter("user2", user2)
                    .getResultList();

            return Optional.of(roomId.get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
