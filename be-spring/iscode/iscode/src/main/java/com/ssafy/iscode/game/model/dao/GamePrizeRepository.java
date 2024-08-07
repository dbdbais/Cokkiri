package com.ssafy.iscode.game.model.dao;

import com.ssafy.iscode.game.model.dto.GameDto;
import com.ssafy.iscode.game.model.dto.GamePrize;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class GamePrizeRepository {

    private final EntityManager em;

    public GamePrizeRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public int save(GamePrize gamePrize) {
        try {
            if (gamePrize.getId() == null) {
                em.persist(gamePrize);
            } else {
                em.merge(gamePrize);
            }

            return 1; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public GamePrize findById(Long id){
        return em.find(GamePrize.class,id);
    }

}
