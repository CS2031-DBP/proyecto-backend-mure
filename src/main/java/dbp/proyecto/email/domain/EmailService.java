package dbp.proyecto.email.domain;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    public void correoSingIn(String to, String name) throws MessagingException {
        Context context = new Context();
        context.setVariable("name", name);

        String process = templateEngine.process("sing-in-template.html", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setTo(to);
        helper.setText(process, true);
        helper.setSubject("Bienvenido a la experiencia con Mure!");

        mailSender.send(message);
    }
}
