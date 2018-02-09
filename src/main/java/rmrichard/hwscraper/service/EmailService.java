package rmrichard.hwscraper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import rmrichard.hwscraper.Properties;
import rmrichard.hwscraper.model.Search;

@Component
public class EmailService {

    @Autowired
    private Properties properties;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(List<Search> searches) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(properties.getRecipient());
            messageHelper.setSubject(properties.getSubject());
            messageHelper.setText(createContent(searches), true);
        };

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException ex) {
            ex.printStackTrace();
        }
    }

    private String createContent(List<Search> searches) {
        Context ctx = new Context();
        ctx.setVariable("searches", searches);
        String htmlContent = templateEngine.process("email", ctx);
        return htmlContent;
    }
}
