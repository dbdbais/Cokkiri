package com.ssafy.iscode.submit.model.dao;

import com.ssafy.iscode.submit.model.dto.Submit;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class SubmitRepository {
    private final EntityManager em;

    public SubmitRepository(EntityManager em) {
        this.em = em;
    }

    public int save(Submit submit){
        try {
            if(submit.getUser() == null || submit.getProblem() == null ){
                return 0;
            }
            else{
                em.persist(submit);
                return 1; // successfully saved
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0; // failed
        }
    }
    public Submit findById(Long no){
        return em.find(Submit.class,no);
    }

    public int getSolvedProblem(String userId) {
        return ((Number) em.createQuery("SELECT COUNT(DISTINCT s.problem.id) FROM Submit s " +
                        "WHERE s.user.id = :userId AND s.correct = true")
                .setParameter("userId", userId)
                .getSingleResult()).intValue();
    }

}
