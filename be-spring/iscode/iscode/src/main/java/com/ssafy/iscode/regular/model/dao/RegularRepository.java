package com.ssafy.iscode.regular.model.dao;

import com.ssafy.iscode.regular.model.dto.RegularDto;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegularRepository {

    private final EntityManager em;

    public RegularRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public int save(RegularDto regularDto) {
        try {
            if (regularDto.getId() == null) {
                em.persist(regularDto);
            } else {
                em.merge(regularDto);
            }

            return 1; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public RegularDto findById(Long id){
        return em.find(RegularDto.class,id);
    }

    public List<RegularDto> findByHost(User host) {
        String query = "SELECT r FROM RegularDto r JOIN r.hostUser u" +
                "WHERE u.nickname = :hostName " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("hostName", host.getNickname())
                .getResultList();
    }

    public List<RegularDto> findAll(int offset){
        String query = "SELECT r FROM RegularDto r " +
                "WHERE r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }

    public List<RegularDto> findByName(String regularName, int offset) {
        String query = "SELECT r FROM RegularDto r " +
                "WHERE r.name LIKE :regularName " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("regularName", "%" + regularName + "%")
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }

    public List<RegularDto> findByWeekday(int weekday, int offset) {
        String query = "SELECT r FROM RegularDto r JOIN r.times t " +
                "WHERE t.weekday = :weekday " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("weekday", weekday)
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }

    public List<RegularDto> findByStart(int start, int offset) {
        String query = "SELECT r FROM RegularDto r JOIN r.times t " +
                "WHERE t.start >= :start " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("start", start)
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }

    public List<RegularDto> findByEnd(int end, int offset) {
        String query = "SELECT r FROM RegularDto r JOIN r.times t " +
                "WHERE t.end <= :end " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("end", end)
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }

    public List<RegularDto> findByTime(int start, int end, int offset) {
        String query = "SELECT r FROM RegularDto r JOIN r.times t " +
                "WHERE t.start >= :start " +
                "AND t.end <= :end " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }

    public List<RegularDto> findByNameWeekday(String regularName, int weekday, int offset) {
        String query = "SELECT r FROM RegularDto r JOIN r.times t " +
                "WHERE r.name LIKE :regularName " +
                "AND t.weekday = :weekday " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("regularName", "%" + regularName + "%")
                .setParameter("weekday", weekday)
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }

    public List<RegularDto> findByNameStart(String regularName, int start, int offset) {
        String query = "SELECT r FROM RegularDto r JOIN r.times t " +
                "WHERE r.name LIKE :regularName " +
                "AND t.start >= :start " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("regularName", "%" + regularName + "%")
                .setParameter("start", start)
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }

    public List<RegularDto> findByNameEnd(String regularName, int end, int offset) {
        String query = "SELECT r FROM RegularDto r JOIN r.times t " +
                "WHERE r.name LIKE :regularName " +
                "AND t.end <= :end " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("regularName", "%" + regularName + "%")
                .setParameter("end", end)
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }

    public List<RegularDto> findByNameTime(String regularName, int start, int end, int offset) {
        String query = "SELECT r FROM RegularDto r JOIN r.times t " +
                "WHERE r.name LIKE :regularName " +
                "AND t.start >= :start " +
                "AND t.end <= :end " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("regularName", "%" + regularName + "%")
                .setParameter("start", start)
                .setParameter("end", end)
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }

    public List<RegularDto> findByWeekdayStart(int weekday, int start, int offset) {
        String query = "SELECT r FROM RegularDto r JOIN r.times t " +
                "WHERE t.weekday = :weekday " +
                "AND t.start >= :start " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("weekday", weekday)
                .setParameter("start", start)
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }

    public List<RegularDto> findByWeekdayEnd(int weekday, int end, int offset) {
        String query = "SELECT r FROM RegularDto r JOIN r.times t " +
                "WHERE t.weekday = :weekday " +
                "AND t.end <= :end " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("weekday", weekday)
                .setParameter("end", end)
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }

    public List<RegularDto> findByWeekdayTime(int weekday, int start, int end, int offset) {
        String query = "SELECT r FROM RegularDto r JOIN r.times t " +
                "WHERE t.weekday = :weekday " +
                "AND t.start >= :start " +
                "AND t.end <= :end " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("weekday", weekday)
                .setParameter("start", start)
                .setParameter("end", end)
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }

    public List<RegularDto> findByNameWeekdayStart(String regularName, int weekday, int start, int offset) {
        String query = "SELECT r FROM RegularDto r JOIN r.times t " +
                "WHERE r.name LIKE :regularName " +
                "AND t.weekday = :weekday " +
                "AND t.start >= :start " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("regularName", "%" + regularName + "%")
                .setParameter("weekday", weekday)
                .setParameter("start", start)
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }

    public List<RegularDto> findByNameWeekdayEnd(String regularName, int weekday, int end, int offset) {
        String query = "SELECT r FROM RegularDto r JOIN r.times t " +
                "WHERE r.name LIKE :regularName " +
                "AND t.weekday = :weekday " +
                "AND t.end <= :end " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("regularName", "%" + regularName + "%")
                .setParameter("weekday", weekday)
                .setParameter("end", end)
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }

    public List<RegularDto> findByNameWeekdayTime(String regularName, int weekday, int start, int end, int offset) {
        String query = "SELECT r FROM RegularDto r JOIN r.times t " +
                "WHERE r.name LIKE :regularName " +
                "AND t.weekday = :weekday " +
                "AND t.start >= :start " +
                "AND t.end <= :end " +
                "AND r.end IS NULL";

        return em.createQuery(query, RegularDto.class)
                .setParameter("regularName", "%" + regularName + "%")
                .setParameter("weekday", weekday)
                .setParameter("start", start)
                .setParameter("end", end)
                .setFirstResult(offset)
                .setMaxResults(4)
                .getResultList();
    }
}
