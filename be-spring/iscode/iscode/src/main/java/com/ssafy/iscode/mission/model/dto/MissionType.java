package com.ssafy.iscode.mission.model.dto;

public enum MissionType {
    SOLVE_PROBLEM("SELECT COUNT(s) FROM Submit s WHERE FUNCTION('DATE', s.createdTime) = CURRENT_DATE AND s.user.id = :userId AND s.correct = TRUE"),
    PLAY_GAME("SELECT COUNT(su) FROM StudyUser su WHERE su.study.end IS NOT NULL AND su.study.isGame AND FUNCTION('DATE', su.study.end) = CURRENT_DATE AND su.user.id = :userId"),
    ATTEND_STUDY("SELECT COUNT(su) FROM StudyUser su WHERE su.study.end IS NOT NULL AND NOT su.study.isGame AND FUNCTION('DATE', su.study.end) = CURRENT_DATE AND su.user.id = :userId"),
    INVITE_FRIEND("SELECT COUNT(uf) FROM UserFriend uf WHERE FUNCTION('DATE', uf.createdTime) = CURRENT_DATE AND uf.user.id = :userId"),
    CREATE_REVIEW("SELECT COUNT(r) FROM Review r WHERE FUNCTION('DATE', r.createdTime) = CURRENT_DATE AND r.user.id = :userId");

    //Mapping SQL QUERY

    private final String sql;

    MissionType(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
