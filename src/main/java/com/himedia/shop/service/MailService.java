package com.himedia.shop.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor  // @Autowired 보다 강력한 자동 주입 어너테이션
public class MailService {

    private final JavaMailSender JMSender;
    private static int number;

    public int sendMail(String email) {
        // 코드 발생
        number = (int)(Math.random() * (90000)) + 100000;

        // 수신 이메일, 제목 내용 등등을 설정할 객체를 생성
        MimeMessage message = JMSender.createMimeMessage();

        try {
            message.setFrom("eunji.030521@gmail.com");  // 보내는 사람 설정
            message.setRecipients(MimeMessage.RecipientType.TO, email);  // 받는 사람 설정
            message.setSubject("이메일 인증");  // 제목 설정

            String body = "";
            body += "<h3>" + "요청하신 인증번호 입니다" + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "감사합니다" + "</h3>";
            message.setText(body, "UTF-8", "html");  // 본문 설정

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        JMSender.send(message);  // 구성 완료된 message를 JMSender로 전송
        return number;
    }
}
