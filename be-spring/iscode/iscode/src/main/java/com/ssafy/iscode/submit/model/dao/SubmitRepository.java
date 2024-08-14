package com.ssafy.iscode.submit.model.dao;

import com.ssafy.iscode.submit.model.dto.SCodeResponseDTO;
import com.ssafy.iscode.submit.model.dto.Submit;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    public List<SCodeResponseDTO> findAllProblemList(String userId, Long problemId) {
//        return em.createQuery(
//                        "SELECT new com.ssafy.iscode.mail.model.dto.SCodeResponseDTO(s.submitCode, s.createdTime) " +
//                                "FROM Submit s " +
//                                "WHERE s.user.id = :userId AND s.problem.id = :problemId AND s.correct = true " +
//                                "ORDER BY s.createdTime ASC", SCodeResponseDTO.class)
//                .setParameter("userId", userId)
//                .setParameter("problemId", problemId)
//                .getResultList();

        List<Submit> ret =  em.createQuery(
                        "SELECT s FROM Submit s " +
                                "WHERE s.user.id = :userId AND s.problem.id = :problemId AND s.correct = true " +
                                "ORDER BY s.createdTime ASC", Submit.class)
                .setParameter("userId", userId)
                .setParameter("problemId", problemId)
                .getResultList();

        List<SCodeResponseDTO> retLst = new ArrayList<>();

        for(Submit s : ret){
            retLst.add(new SCodeResponseDTO(s.getSubmit_code(),s.getCreatedTime()));
        }
        return retLst;
    }


    public List<Long> getSolvedProblem(String userId) {
        return em.createQuery("SELECT DISTINCT s.problem.id FROM Submit s " +
                        "WHERE s.user.id = :userId AND s.correct = true", Long.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public boolean canReview(String userId, Long problemId) {
        Long count = (Long) em.createQuery(
                        "SELECT COUNT(s) FROM Submit s WHERE s.user.id = :userId AND s.problem.id = :problemId"
                )
                .setParameter("userId", userId)
                .setParameter("problemId", problemId)
                .getSingleResult();

        System.out.println("COUNT : "  + count);

        return count >= 3;
    }
}
