package com.ssafy.iscode.regular.model.dao;

import com.ssafy.iscode.regular.model.dto.RegularDto;
import com.ssafy.iscode.regular.model.dto.RegularUser;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegularUserRepository {

    private final EntityManager em;

    public RegularUserRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public int save(RegularUser regularUser) {
        try {
            em.persist(regularUser);
            return 1; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public RegularUser findById(Long id){
        return em.find(RegularUser.class,id);
    }

    @Transactional
    public int remove(Long id){

        RegularUser delUser = findById(id);
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

    public List<RegularUser> findDuffl(RegularDto regularDto, User host) {
        String query = "SELECT ru FROM RegularUser ru " +
                "WHERE ru.user.nickname = :hostName " +
                "AND ru.regular.id = :regularId " +
                "AND ru.regular.end IS NULL";

        return em.createQuery(query, RegularUser.class)
                .setParameter("hostName", host.getNickname())
                .setParameter("regularId", regularDto.getId())
                .getResultList();
    }
}
