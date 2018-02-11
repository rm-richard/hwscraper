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
import rmrichard.hwscraper.model.SearchResult;

@Component
public class EmailService {

    @Autowired
    private Properties properties;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(List<SearchResult> searchResults) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(properties.getRecipient());
            messageHelper.setSubject(properties.getSubject());
            messageHelper.setText(createContent(searchResults), true);
        };

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException ex) {
            ex.printStackTrace();
        }
    }

    private String createContent(List<SearchResult> searchResults) {
        Context ctx = new Context();
        ctx.setVariable("searchResults", searchResults);
        String htmlContent = templateEngine.process("email", ctx);
        return htmlContent;
    }
}
