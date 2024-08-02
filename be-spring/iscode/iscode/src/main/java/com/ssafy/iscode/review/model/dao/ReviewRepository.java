package com.ssafy.iscode.review.model.dao;

import com.ssafy.iscode.review.model.dto.Review;
import com.ssafy.iscode.review.model.dto.ReviewRequestDTO;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ReviewRepository {

    private final EntityManager em;

    public ReviewRepository(EntityManager em) {
        this.em = em;
    }

    public int save(Review review){
        try {
            if(review.getContent() ==null || review.getProblem() == null || review.getUser() == null){
                return 0; // failed
            }
                em.persist(review);
            return 1; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // failed
        }
    }

    public Review findById(Long rid){
        return em.find(Review.class,rid);
    }

    public int remove(Long id){

        Review delReview = findById(id);
        if(delReview != null){
            em.remove(delReview);
            return 1;
            // successfully deleted
        }
        else{
            return 0;
            // failed
        }
    }

    public List<Review> findAllReviewByPID(Long pid){
        return em.createQuery("SELECT r FROM Review r WHERE r.problem.id = :pid", Review.class)
                .setParameter("pid", pid)
                .getResultList();
    }


}
