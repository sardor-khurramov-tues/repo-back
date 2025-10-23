package repo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromMail;

    private final JavaMailSender javaMailSender;

    public String generatePasswordAndSendEmail(String email) {
//        String newPassword = RandomStringUtils.random(12, true, true);
//        sendEmail(
//                email,
//                "Welcome to TUES Repository",
//                String.format("Welcome to TUES Repository%n%nYour new password: %s %n", newPassword)
//        );
//        return newPassword;

        return "12345678";
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }

}
