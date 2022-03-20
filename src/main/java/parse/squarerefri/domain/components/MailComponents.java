package parse.squarerefri.domain.components;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import parse.squarerefri.domain.mail.entity.MailTemplate;
import parse.squarerefri.domain.mail.repository.MailTemplateRepository;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MailComponents {

    private final JavaMailSender javaMailSender;
    private final MailTemplateRepository mailTemplateRepository;

    public boolean sendMail(String mailTemplateId, String mail, String userName, String uuid) {

        Optional<MailTemplate> optionalMailTemplate = mailTemplateRepository.findByMailTemplateId(mailTemplateId);
        if (!optionalMailTemplate.isPresent()) {
            return false;
        }

        MailTemplate mailTemplate = optionalMailTemplate.get();

        boolean result = false;

        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo(mail);
                mimeMessageHelper.setSubject(mailTemplate.getSubject());
                mimeMessageHelper.setText(mailTemplate.textAdd(userName, uuid), true);
            }
        };

        try {
            javaMailSender.send(msg);
            result = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }


}
