package com.ssafy.iscode.mail.service;

import com.ssafy.iscode.mail.model.dto.VerificationCode;
import jakarta.activation.DataHandler;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.MimeUtility;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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

    public MimeMessage createPWMail(String mail){
        int code = createRandomNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("[코딩하는 사람 끼리 스터디] 비밀번호 재설정");
            String body = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>";
            body += "<h2 style='text-align: center; color: #5A79C1;'>비밀번호 초기화 코드</h2>";
            body += "<p style='font-size: 16px; color: #333;'>안녕하세요,</p>";
            body += "<p style='font-size: 16px; color: #333;'>비밀번호를 재설정하려면 아래의 인증 코드를 입력해주세요:</p>";
            body += "<div style='text-align: center; margin: 20px 0;'>";
            body += "<span style='display: inline-block; font-size: 24px; font-weight: bold; color: #5A79C1; padding: 10px 20px; border: 2px dashed #5A79C1; border-radius: 5px;'>" + code + "</span>";
            body += "</div>";
            body += "<p style='font-size: 16px; color: #333;'>이 요청을 본인이 하지 않으셨다면, 이 이메일을 무시하셔도 됩니다.</p>";
            body += "<p style='font-size: 16px; color: #333;'>감사합니다.<br/><strong>코끼리 팀</strong></p>";
            body += "<hr style='border-top: 1px solid #ddd;'>";
            body += "<p style='font-size: 12px; color: #999; text-align: center;'>이 메일은 자동으로 발송된 메일입니다. 회신하지 마세요.</p>";
            body += "<img src='cid:elephantImage' style='width: 100px; height: auto; display: block; margin: 0 auto;' />";
            body += "</div>";

            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(body, "text/html; charset=UTF-8");

            // 이미지 파일 추가 (상대 경로로 지정)
            MimeBodyPart imagePart = new MimeBodyPart();
            ClassPathResource resource = new ClassPathResource("static/img/logo.png");
            //DataSource fds = new FileDataSource(resource.getFile());
            InputStream inputStream = resource.getInputStream();
            byte[] imageBytes = inputStream.readAllBytes();
            //read all bytes
            ByteArrayDataSource dataSource = new ByteArrayDataSource(imageBytes, "image/png");
            imagePart.setDataHandler(new DataHandler(dataSource));
            imagePart.setHeader("Content-ID", "<elephantImage>");
            imagePart.setFileName(MimeUtility.encodeText("logo.png", "UTF-8", null));

            // 본문과 이미지를 하나의 Multipart로 합침
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);

            //message.setText(body,"UTF-8", "html");

            verificationCodeMap.put(mail,new VerificationCode(code, LocalDateTime.now().plusMinutes(5)));

        } catch (MessagingException |IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public MimeMessage setPWMail(String mail, String tempPassword ){
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("[코딩하는 사람 끼리 스터디] 임시 비밀번호 발급");
            String body = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>";
            body += "<h2 style='text-align: center; color: #5A79C1;'>임시 비밀번호 발급 안내</h2>";
            body += "<p style='font-size: 16px; color: #333;'>안녕하세요,</p>";
            body += "<p style='font-size: 16px; color: #333;'>비밀번호 재설정을 요청하셔서 아래와 같이 임시 비밀번호를 발급해 드립니다:</p>";
            body += "<p style='font-size: 16px; color: #333;'><strong>임시 비밀번호로 로그인한 후, 반드시 새로운 비밀번호로 변경해 주세요.</strong></p>";
            body += "<div style='text-align: center; margin: 20px 0;'>";
            body += "<span style='display: inline-block; font-size: 24px; font-weight: bold; color: #5A79C1; padding: 10px 20px; border: 2px dashed #5A79C1; border-radius: 5px;'>" + tempPassword + "</span>";
            body += "</div>";

            body += "<p style='font-size: 16px; color: #333;'>이 요청을 본인이 하지 않으셨다면, 이 이메일을 무시하셔도 됩니다.</p>";
            body += "<p style='font-size: 16px; color: #333;'>감사합니다.<br/><strong>코끼리 팀</strong></p>";
            body += "<hr style='border-top: 1px solid #ddd;'>";
            body += "<p style='font-size: 12px; color: #999; text-align: center;'>이 메일은 자동으로 발송된 메일입니다. 회신하지 마세요.</p>";
            body += "<img src='cid:elephantImage' style='width: 100px; height: auto; display: block; margin: 0 auto;' />";
            body += "</div>";

            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(body, "text/html; charset=UTF-8");

            // 이미지 파일 추가 (상대 경로로 지정)
            MimeBodyPart imagePart = new MimeBodyPart();
            ClassPathResource resource = new ClassPathResource("static/img/logo.png");
            //DataSource fds = new FileDataSource(resource.getFile());
            InputStream inputStream = resource.getInputStream();
            byte[] imageBytes = inputStream.readAllBytes();
            //read all bytes
            ByteArrayDataSource dataSource = new ByteArrayDataSource(imageBytes, "image/png");
            imagePart.setDataHandler(new DataHandler(dataSource));
            imagePart.setHeader("Content-ID", "<elephantImage>");
            imagePart.setFileName(MimeUtility.encodeText("logo.png", "UTF-8", null));


            // 본문과 이미지를 하나의 Multipart로 합침
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);

        } catch (MessagingException |IOException e) {
            e.printStackTrace();

        }
        return message;
    }

    public MimeMessage createRegisterMail(String mail){
        int code = createRandomNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("[코딩하는 사람 끼리 스터디] 회원가입 인증 코드");
            String body = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>";
            body += "<h2 style='text-align: center; color: #5A79C1;'>회원가입 인증 코드</h2>";
            body += "<p style='font-size: 16px; color: #333;'>안녕하세요,</p>";
            body += "<p style='font-size: 16px; color: #333;'>회원가입을 하시려면 아래의 인증 코드를 입력해주세요:</p>";
            body += "<div style='text-align: center; margin: 20px 0;'>";
            body += "<span style='display: inline-block; font-size: 24px; font-weight: bold; color: #5A79C1; padding: 10px 20px; border: 2px dashed #5A79C1; border-radius: 5px;'>" + code + "</span>";
            body += "</div>";
            body += "<p style='font-size: 16px; color: #333;'>이 요청을 본인이 하지 않으셨다면, 이 이메일을 무시하셔도 됩니다.</p>";
            body += "<p style='font-size: 16px; color: #333;'>감사합니다.<br/><strong>코끼리 팀</strong></p>";
            body += "<hr style='border-top: 1px solid #ddd;'>";
            body += "<p style='font-size: 12px; color: #999; text-align: center;'>이 메일은 자동으로 발송된 메일입니다. 회신하지 마세요.</p>";
            body += "<img src='cid:elephantImage' style='width: 100px; height: auto; display: block; margin: 0 auto;' />";
            body += "</div>";

            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(body, "text/html; charset=UTF-8");

            // 이미지 파일 추가 (상대 경로로 지정)
            MimeBodyPart imagePart = new MimeBodyPart();
            ClassPathResource resource = new ClassPathResource("static/img/logo.png");
            InputStream inputStream = resource.getInputStream();
            byte[] imageBytes = inputStream.readAllBytes();
            //read all bytes
            ByteArrayDataSource dataSource = new ByteArrayDataSource(imageBytes, "image/png");
            imagePart.setDataHandler(new DataHandler(dataSource));
            imagePart.setHeader("Content-ID", "<elephantImage>");
            imagePart.setFileName(MimeUtility.encodeText("logo.png", "UTF-8", null));

            // 본문과 이미지를 하나의 Multipart로 합침
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);

            //message.setText(body,"UTF-8", "html");

            verificationCodeMap.put(mail,new VerificationCode(code, LocalDateTime.now().plusMinutes(5)));

        } catch (MessagingException |IOException e) {
            e.printStackTrace();
        }
        return message;

    }

    public void sendRMail(String mail){
        MimeMessage message = createRegisterMail(mail);
        javaMailSender.send(message);
    }
    public void sendPMail(String mail){
        MimeMessage message = createPWMail(mail);
        javaMailSender.send(message);
    }
    public void resetPW(String mail, String tempPW){
        MimeMessage message = setPWMail(mail,tempPW);
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
