package com.ssafy.iscode.mission.service;

import com.ssafy.iscode.mission.model.dto.Mission;
import com.ssafy.iscode.mission.model.dto.MissionType;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MissionService {

    private ArrayList<int[]> combination() {
        int n = missionTypes.length;
        ArrayList<int[]> combinations = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    int[] combo = {i, j, k};
                    combinations.add(combo);
                }
            }
        }

        return combinations;
    }

    private final MissionType[] missionTypes = MissionType.values();

    private final Random random = new Random();

    public List<MissionType> assignRandomMission(){
        ArrayList<int[]> com = combination();
        List<MissionType> lst = new ArrayList<>();
        int randIdx = random.nextInt(com.size());

        int[] sel = com.get(randIdx);

        for(int idx : sel){
            System.out.println(idx);
            MissionType ranMissionType = missionTypes[idx];
            lst.add(ranMissionType);
        }

        return lst;

        //create random mission
    }

}
