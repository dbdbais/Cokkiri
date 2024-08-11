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

    // 인증 이메일 전송
    @PostMapping("/send")
    public HashMap<String, Object> mailSend( @RequestParam String mail) {
        HashMap<String, Object> map = new HashMap<>();

        try {
            mailService.sendMail(mail);

            map.put("success", Boolean.TRUE);
        } catch (Exception e) {
            map.put("success", Boolean.FALSE);
            map.put("error", e.getMessage());
        }

        return map;
    }

    // 인증번호 일치여부 확인
    @GetMapping("/Check")
    public ResponseEntity<?> mailCheck(@RequestParam String mail, @RequestParam String userNumber) {

        boolean isMatch = mailService.checkCode(mail,userNumber);

        return ResponseEntity.ok(isMatch);
    }

}
