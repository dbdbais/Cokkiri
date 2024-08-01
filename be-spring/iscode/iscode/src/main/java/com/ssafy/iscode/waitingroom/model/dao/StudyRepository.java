package com.ssafy.iscode.waitingroom.model.dao;

import com.ssafy.iscode.waitingroom.model.dto.StudyDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudyRepository {

    private final EntityManager em;

    public StudyRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public int save(StudyDto study) {
        try {
            if (study.getId() == null) {
                em.persist(study);
            } else {
                em.merge(study);
            }

            return 1; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public StudyDto findById(Long id){
        return em.find(StudyDto.class,id);
    }

    public List<StudyDto> findAll(int offset){
        String query = "SELECT s FROM StudyDto s " +
                "WHERE s.isOpen";

        return em.createQuery(query, StudyDto.class)
                .setFirstResult(offset)
                .setMaxResults(6)
                .getResultList();
    }

    public List<StudyDto> findByName(String roomName, int offset) {
        String query = "SELECT s FROM StudyDto s " +
                "WHERE s.name LIKE :roomName " +
                "AND s.isOpen";

        return em.createQuery(query, StudyDto.class)
                .setParameter("roomName", "%" + roomName + "%")
                .setFirstResult(offset)
                .setMaxResults(6)
                .getResultList();
    }

    public List<StudyDto> findSelectedAll(boolean isGame, int offset){
        String query = "SELECT s FROM StudyDto s " +
                "WHERE s.isOpen " +
                "AND s.isGame = :isGame";

        return em.createQuery(query, StudyDto.class)
                .setParameter("isGame", isGame)
                .setFirstResult(offset)
                .setMaxResults(6)
                .getResultList();
    }

    public List<StudyDto> findSelectedByName(String roomName, boolean isGame, int offset) {
        String query = "SELECT s FROM StudyDto s " +
                "WHERE s.name LIKE :roomName " +
                "AND s.isOpen AND s.isGame = :isGame";

        return em.createQuery(query, StudyDto.class)
                .setParameter("roomName", "%" + roomName + "%")
                .setParameter("isGame", isGame)
                .setFirstResult(offset)
                .setMaxResults(6)
                .getResultList();
    }
}
