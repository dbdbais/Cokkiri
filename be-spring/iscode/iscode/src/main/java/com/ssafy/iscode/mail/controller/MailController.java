package com.ssafy.iscode.mail.controller;


import com.ssafy.iscode.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    // send register E-MAIL
    @PostMapping("/register")
    public HashMap<String, Object> registerSend( @RequestParam String mail) {
        HashMap<String, Object> map = new HashMap<>();

        try {
            mailService.sendRMail(mail);

            map.put("success", Boolean.TRUE);
        } catch (Exception e) {
            map.put("success", Boolean.FALSE);
            map.put("error", e.getMessage());
        }

        return map;
    }

    // send password E-MAIL
    @PostMapping("/password")
    public HashMap<String, Object> passwordSend( @RequestParam String mail) {
        HashMap<String, Object> map = new HashMap<>();

        try {
            mailService.sendPMail(mail);

            map.put("success", Boolean.TRUE);
        } catch (Exception e) {
            map.put("success", Boolean.FALSE);
            map.put("error", e.getMessage());
        }

        return map;
    }

    // 인증번호 일치여부 확인
    @GetMapping("/check")
    public ResponseEntity<?> mailCheck(@RequestParam String mail, @RequestParam String userNumber) {

        boolean isMatch = mailService.checkCode(mail,userNumber);

        return ResponseEntity.ok(isMatch);
    }

}
