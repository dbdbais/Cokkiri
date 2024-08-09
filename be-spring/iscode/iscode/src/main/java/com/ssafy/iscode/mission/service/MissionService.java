package com.ssafy.iscode.mission.service;

import com.ssafy.iscode.mission.model.dto.Mission;
import com.ssafy.iscode.mission.model.dto.MissionType;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MissionService {
//
    private final MissionType[] missionTypes = MissionType.values();

    private Random random = new Random();

    public MissionType assignRandomMission(){
        int randIdx = random.nextInt(missionTypes.length);
        MissionType ranMissionType = missionTypes[randIdx];
        return ranMissionType;
        //create random mission
    }

}
