package com.ssafy.iscode.mission.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mission {

    @Enumerated(value = EnumType.STRING)
    private MissionType missionType;

    public Mission(MissionType missionType) {
        this.missionType = missionType;
    }


}
