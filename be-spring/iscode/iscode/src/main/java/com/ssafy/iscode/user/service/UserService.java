package com.ssafy.iscode.user.service;

import com.ssafy.iscode.mission.model.dto.MissionType;
import com.ssafy.iscode.mission.service.MissionService;
import com.ssafy.iscode.regular.model.dto.RegularUser;
import com.ssafy.iscode.review.model.dao.ReviewRepository;
import com.ssafy.iscode.user.model.dao.UserRepository;
import com.ssafy.iscode.user.model.dto.Status;
import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.user.model.dto.UserFriend;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final ReviewRepository reviewRepository;

    private final MissionService missionService;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, ReviewRepository reviewRepository, MissionService missionService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.missionService = missionService;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public User getUser(String id){
        return userRepository.findById(id);
    }
    public List<UserFriend> getUserFriends(String id){
        return userRepository.getFriendList(id);
    }

    // Schedule to run at midnight every day
    @Transactional
    //@Scheduled(cron = "0 0 0 * * ?") //for real
    //@Scheduled(cron = "0 */2 * * * *")  // 매 2분마다 실행
    public void assignDailyMissions() {
        System.out.println("dailyMission Generated");
        List<User> users = userRepository.findAll(); // Fetch all users


        for (User user : users) {
            user.resetMission();
            //reset user's mission
            List<MissionType> randomMission = missionService.assignRandomMission();
            // assign random mission
            for(MissionType msn : randomMission){
                user.setMission(msn, false); // Assign random mission and reset completion
            }

            userRepository.modify(user); // Save the updated user
            System.out.println(user);
        }
    }

    @Transactional
    //@Scheduled(cron = "0 0/30 * * * ?") //for real
    @Scheduled(cron = "0 * * * * *")  // 매 1분마다 실행
    public void isMissionAccomplished(){
        System.out.println("mission accomplished? ");
        List<User> users = userRepository.findAllWithIncompleteMissions();

        for(User user : users){
            System.out.println(user.getId());

            boolean isAccomplished = false;
            Map<MissionType, Boolean> mp = user.getMission();

            for(MissionType mt : mp.keySet()){
                if(mp.get(mt)) continue;
                //if done continue
                System.out.println(mt);
                if(userRepository.isMissionAccomplished(user,mt)){
                    isAccomplished = true;
                    user.setMission(mt,true);
                }
            }

            if(isAccomplished){
                userRepository.modify(user);
            }

        }
    }

    public int insertUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //ENCRYPT PW
        List<MissionType> dailyMS = missionService.assignRandomMission();
        //Insert
        for(MissionType msn : dailyMS){
            user.setMission(msn,false);
        }
        return userRepository.save(user);
    }
    public int modifyUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //ENCRYPT PW
        return userRepository.modify(user);
    }

    public String resetPassword(User user){
        // 비밀번호 길이 설정
        int passwordLength = 12; // 원하는 비밀번호 길이

        // 비밀번호 생성기 초기화
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new SecureRandom(); // 보안적으로 더 안전한 랜덤 생성기
        StringBuilder sb = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        String rawPassword = sb.toString();

        String encryptedPassword = passwordEncoder.encode(rawPassword);



        user.setPassword(encryptedPassword);

        userRepository.modify(user);

        return rawPassword;
    }

    public int deleteUser(String id){
        reviewRepository.makeUserNull(id);
        //make all review by user NULL
        return userRepository.remove(id);
    }

    public int acceptFriend(String userId,String friendUserId){
        return userRepository.accept(userId,friendUserId);
    }

    public int insertFriend(String userId, String friendUserId){
        return userRepository.saveFriend(userId,friendUserId);
    }

    public int deleteFriend(String userId, String friendUserId){
        return userRepository.removeFriend(userId,friendUserId);
    }
    public int login(String user_id, String password){
        User gUser = userRepository.findById(user_id);

        if(gUser == null) return 0;

        if(passwordEncoder.matches(password, gUser.getPassword())){
            return 1;
            //if password correct
        }
        else{
            return 0;
        }
    }
    //call when problem is correct
    public int plusScore(String uid, int pLevel ){
        return userRepository.scoreAchieved(uid,pLevel);
    }


    public List<Long> getRegular(String userName) {
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Long> list = new ArrayList<>();

        for(RegularUser regular: user.getRegulars()) {
            if(regular.getIsAccept()) {
                list.add(regular.getRegular().getId());
            }
        }

        return list;
    }

    public User getUserByName(String userName) {
        return userRepository.findByName(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


}
