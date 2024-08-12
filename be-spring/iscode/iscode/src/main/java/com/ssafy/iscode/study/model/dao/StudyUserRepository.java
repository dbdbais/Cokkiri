package com.ssafy.iscode.study.model.dao;

import com.ssafy.iscode.regular.model.dto.RegularDto;
import com.ssafy.iscode.study.model.dto.StudyUser;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudyUserRepository {

    private final EntityManager em;

    public StudyUserRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public int save(StudyUser studyUser) {
        try {
            em.persist(studyUser);
            return 1; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public StudyUser findById(Long id){
        return em.find(StudyUser.class,id);
    }

    @Transactional
    public int remove(Long id){

        StudyUser delUser = findById(id);
        if(delUser != null){
            em.remove(delUser);
            return 1;
            // successfully deleted
        }
        else{
            return 0;
            // failed
        }
    }

    public Optional<StudyUser> findByUser(Long roomId, String userName) {
        String query = "SELECT su FROM StudyUser su " +
                "JOIN su.study s JOIN su.user u " +
                "WHERE s.id = :roomId " +
                "AND u.nickname = :userName";
        try {
            List<StudyUser> list =  em.createQuery(query, StudyUser.class)
                    .setParameter("roomId", roomId)
                    .setParameter("userName", userName)
                    .getResultList();

            return Optional.of(list.get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
