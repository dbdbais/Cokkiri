package com.ssafy.iscode.problem.model.dao;

import com.ssafy.iscode.problem.model.dto.Problem;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ProblemRepository{


    private final EntityManager em;

    public ProblemRepository(EntityManager em) {
        this.em = em;
    }
    //Create, Update
    public void save(Problem problem){
        em.persist(problem);
    }

    public Problem findById(Long id){
        return em.find(Problem.class,id);
    }

    public void remove(Long id){
        em.remove(findById(id));
    }

    //select ALL
    public List<Problem> findAll(){
        return em.createQuery("SELECT p FROM Problem p", Problem.class)
                .getResultList();
    }


}
