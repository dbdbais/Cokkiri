package com.ssafy.iscode.message.model.dao;

import com.ssafy.iscode.message.model.dto.MessageRoomUser;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class MessageRoomUserRepository {

    private final EntityManager em;

    public MessageRoomUserRepository(EntityManager em) {
        this.em = em;
    }

    public int save(MessageRoomUser roomUser) {
//        try {
//
//            if(findById(roomUser.getId()) == null){
                //Insert
                em.persist(roomUser);
//            }
//            else{
//                //Modify
//                em.merge(roomUser);
//            }
            return 1; // successfully saved
//        } catch (Exception e) {
//            e.printStackTrace();;
//            return 0; // failed
//        }
    }

    public MessageRoomUser findById(Long id){
        return em.find(MessageRoomUser.class,id);
    }
}
