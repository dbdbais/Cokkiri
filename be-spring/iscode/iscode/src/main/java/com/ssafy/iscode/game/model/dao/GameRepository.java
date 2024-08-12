package com.ssafy.iscode.game.model.dao;

import com.ssafy.iscode.game.model.dto.GameDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepository {

    private final EntityManager em;

    public GameRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public int save(GameDto gameDto) {
        try {
            if (findById(gameDto.getId()) == null) {
                em.persist(gameDto);
            } else {
                em.merge(gameDto);
            }

            return 1; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public GameDto findById(Long id){
        return em.find(GameDto.class,id);
    }


}
