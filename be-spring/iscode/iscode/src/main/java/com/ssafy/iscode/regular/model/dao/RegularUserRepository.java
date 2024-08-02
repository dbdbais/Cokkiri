package com.ssafy.iscode.regular.model.dao;

import com.ssafy.iscode.regular.model.dto.RegularUser;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class RegularUserRepository {

    private final EntityManager em;

    public RegularUserRepository(EntityManager em) {
        this.em = em;
    }

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
}
