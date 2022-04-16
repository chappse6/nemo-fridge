package parse.squarerefri.domain.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import parse.squarerefri.domain.mail.entity.MailTemplate;
import parse.squarerefri.domain.mail.repository.MailTemplateRepository;

import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class MailComponents {

    private final JavaMailSender javaMailSender;
    private final MailTemplateRepository mailTemplateRepository;

    private final TemplateEngine htmlTemplateEngine;

    public boolean sendMail(String mailTemplateId, String mail, String userName, String uuid) {

        Optional<MailTemplate> optionalMailTemplate = mailTemplateRepository.findByMailTemplateId(mailTemplateId);
        if (!optionalMailTemplate.isPresent()) {
            return false;
        }

        MailTemplate mailTemplate = optionalMailTemplate.get();

        Map<String, Object> variables = mailTemplate.variablesCreate(userName, uuid);

        Context context = new Context();
        context.setVariables(variables);

        String htmlTemplate = htmlTemplateEngine.process("mail/mail", context);

        boolean result = false;

        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo(mail);
                mimeMessageHelper.setSubject(mailTemplate.getSubject());
                mimeMessageHelper.setText(htmlTemplate, true);
            }
        };

        try {
            javaMailSender.send(msg);
            result = true;

        } catch (Exception e) {
            log.error("e.getMessage={}",e.getMessage());
        }

        return result;
    }


}
