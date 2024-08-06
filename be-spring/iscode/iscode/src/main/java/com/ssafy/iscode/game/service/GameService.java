package com.ssafy.iscode.game.service;

import com.ssafy.iscode.game.model.dao.GameRepository;
import com.ssafy.iscode.user.model.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;


}
