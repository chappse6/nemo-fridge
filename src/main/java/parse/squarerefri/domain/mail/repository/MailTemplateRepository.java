package parse.squarerefri.domain.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import parse.squarerefri.domain.mail.entity.MailTemplate;

import java.util.Optional;

public interface MailTemplateRepository extends JpaRepository<MailTemplate, String> {

    Optional<MailTemplate> findByMailTemplateId(String mailTemplateId);
}
