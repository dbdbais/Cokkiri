package com.ssafy.iscode.user.model.dao;

import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UserRepository {

    private final EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    public int save(User user) {
        try {

            if(findById(user.getId()) == null){
                //Insert
                em.persist(user);
            }
            else{
                //Modify
                em.merge(user);
            }
            return 1; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // failed
        }
    }

    public User findById(String id){
        return em.find(User.class,id);
    }

    public int remove(String id){

        User delUser = findById(id);
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

    public List<User> findAll(){
        return em.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

}
