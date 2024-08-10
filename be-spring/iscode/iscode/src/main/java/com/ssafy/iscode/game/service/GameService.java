package com.ssafy.iscode.game.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.iscode.game.model.dao.GamePrizeRepository;
import com.ssafy.iscode.game.model.dao.GameRepository;
import com.ssafy.iscode.game.model.dto.GameDto;
import com.ssafy.iscode.game.model.dto.GamePrize;
import com.ssafy.iscode.problem.model.dao.ProblemRepository;
import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.study.model.dao.StudyRepository;
import com.ssafy.iscode.study.model.dto.StudyDto;
import com.ssafy.iscode.user.model.dao.UserRepository;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePrizeRepository gamePrizeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Transactional
    public int updateGame(Map<String, String> params) {
        GameDto gameDto = new GameDto(
                Long.parseLong(params.get("sessionId")),
                Integer.parseInt(params.get("minDifficulty")),
                Integer.parseInt(params.get("maxDifficulty")),
                Long.parseLong(params.get("time")),
                Integer.parseInt(params.get("mode"))
        );

        try {
            gameRepository.save(gameDto);

            updateProblem(gameDto.getId(), gameDto.getMinDifficulty(), gameDto.getMaxDifficulty());

            return 1;
        } catch (Exception e) {
            e.printStackTrace();

            return 0;
        }
    }

    public GameDto getGame(Long sessionId) {
        return gameRepository.findById(sessionId);
    }

    private int updateProblem(Long sessionId, int minDifficulty, int maxDifficulty) {
        try {
            List<Problem> problemAll = problemRepository.findAll();
            List<Problem> problems = problemAll.stream()
                    .filter(problem -> problem.getLevel() >= minDifficulty && problem.getLevel() <= maxDifficulty)
                    .toList();

            if (problems.size() < 2) {
                throw new IllegalStateException("Problem not enough");
            }

            Random random = new Random();

            List<Problem> result = random.ints(0, problems.size())
                    .distinct()
                    .limit(2)
                    .mapToObj(problems::get)
                    .collect(Collectors.toList());

            StudyDto studyDto = studyRepository.findById(sessionId);

            if(studyDto == null) {
                throw new RuntimeException("Study not found");
            }

            studyDto.setProblems(result);
            studyRepository.save(studyDto);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();

            return 0;
        }
    }

    public int inputRankUser(Long sessionId, String userName, int rank, long time) {
        GameDto gameDto = gameRepository.findById(sessionId);

        if(gameDto == null) {
            throw  new RuntimeException("Game not found");
        }

        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GamePrize gamePrize = new GamePrize(null, gameDto, user, rank, time);

        try {
            gamePrizeRepository.save(gamePrize);

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
