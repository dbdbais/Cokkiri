package com.ssafy.iscode.rcomment.model.dao;

import com.ssafy.iscode.rcomment.model.dto.ReviewComment;
import com.ssafy.iscode.rcomment.model.dto.ReviewCommentResponseDTO;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ReviewCommentRepository {
    private final EntityManager em;

    public ReviewCommentRepository(EntityManager em) {
        this.em = em;
    }

    public int save(ReviewComment reviewComment){
        try{
            if(reviewComment.getReview() == null || reviewComment.getWriter() == null ||
                    reviewComment.getContent() == null){
                return 0;
            }
            em.persist(reviewComment);
            return 1;
        } catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public int modify(ReviewComment reviewComment){
        try{
            if(reviewComment.getReview() == null || reviewComment.getWriter() == null ||
                    reviewComment.getContent() == null){
                return 0;
            }
            em.merge(reviewComment);
            return 1;
        } catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public ReviewComment findById(Long cid){return em.find(ReviewComment.class,cid); }

    public int deleteComment(ReviewComment reviewComment){
        try{
            if(reviewComment == null){
                return 0;
            }
            if(reviewComment.getChildren().isEmpty()){
               em.remove(reviewComment);
            }
            else{
                reviewComment.setDeleted(true);
                em.merge(reviewComment);
            }
            return 1;
        } catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    // get review comment
    public List<ReviewCommentResponseDTO> findAllByReviewId(Long reviewId) {
        List<ReviewComment> comments = em.createQuery(
                        "SELECT DISTINCT rc FROM ReviewComment rc " +
                                "LEFT JOIN FETCH rc.children " +
                                "WHERE rc.review.id = :reviewId AND rc.parent IS NULL", ReviewComment.class)
                .setParameter("reviewId", reviewId)
                .getResultList();

        return comments.stream()
                .map(ReviewComment::toResponseDTO)
                .collect(Collectors.toList());
    }


}
