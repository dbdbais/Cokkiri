package com.ssafy.iscode.mail.service;

import com.ssafy.iscode.mail.model.dto.VerificationCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String senderEmail;

    //key : email -> value : code
    private Map<String, VerificationCode> verificationCodeMap = new HashMap<>();

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public int createRandomNumber(){
        return (int)(Math.random() * (90000)) +100000;
    }

    public MimeMessage createMail(String mail){
        int code = createRandomNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>";
            body += "<h2 style='text-align: center; color: #4CAF50;'>비밀번호 초기화 코드</h2>";
            body += "<p style='font-size: 16px; color: #333;'>안녕하세요,</p>";
            body += "<p style='font-size: 16px; color: #333;'>비밀번호를 재설정하려면 아래의 인증 코드를 입력해주세요:</p>";
            body += "<div style='text-align: center; margin: 20px 0;'>";
            body += "<span style='display: inline-block; font-size: 24px; font-weight: bold; color: #4CAF50; padding: 10px 20px; border: 2px dashed #4CAF50; border-radius: 5px;'>" + code + "</span>";
            body += "</div>";
            body += "<p style='font-size: 16px; color: #333;'>이 요청을 본인이 하지 않으셨다면, 이 이메일을 무시하셔도 됩니다.</p>";
            body += "<p style='font-size: 16px; color: #333;'>감사합니다.<br/><strong>코끼리 팀</strong></p>";
            body += "<hr style='border-top: 1px solid #ddd;'>";
            body += "<p style='font-size: 12px; color: #999; text-align: center;'>이 메일은 자동으로 발송된 메일입니다. 회신하지 마세요.</p>";
            body += "</div>";
            message.setText(body,"UTF-8", "html");

            verificationCodeMap.put(mail,new VerificationCode(code, LocalDateTime.now().plusMinutes(5)));

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;

    }

    public void sendMail(String mail){
        MimeMessage message = createMail(mail);
        javaMailSender.send(message);

    }

    public boolean checkCode(String mail, String userNumber){
        VerificationCode verificationCode = verificationCodeMap.get(mail);
        if (verificationCode == null || verificationCode.getExpirationTime().isBefore(LocalDateTime.now())) {
            System.out.println("Wrong CODE or EXPIRED");
            return false;
        }
        System.out.println("인증 번호 : "+verificationCode);
        return userNumber.equals(String.valueOf(verificationCode.getCode()));

    }

}
