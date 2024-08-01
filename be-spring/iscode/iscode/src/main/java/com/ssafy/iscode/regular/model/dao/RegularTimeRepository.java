package com.ssafy.iscode.regular.model.dao;

import com.ssafy.iscode.regular.model.dto.RegularTimeDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class RegularTimeRepository {

    private final EntityManager em;

    public RegularTimeRepository(EntityManager em) {
        this.em = em;
    }

    public int save(RegularTimeDto regularTimeDto) {
        try {
            em.persist(regularTimeDto);
            return 1; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public RegularTimeDto findById(Long id){
        return em.find(RegularTimeDto.class,id);
    }

    public int remove(Long id){

        RegularTimeDto delTime = findById(id);
        if(delTime != null){
            em.remove(delTime);
            return 1;
            // successfully deleted
        }
        else{
            return 0;
            // failed
        }
    }
}
