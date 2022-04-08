package parse.squarerefri.domain.mail.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class MailTemplate implements MailTemplateType{

    @Id
    String mailTemplateId;

    String subject;
    String text;

    String article;
    String link;
    String linkMsg;

    // 비지니스 로직
    public String textAdd(String userName, String uuid) {
        return String.format(this.text, userName, uuid);
    }

    public Map<String, Object> variablesCreate(String userName, String uuid) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("subheader",userName + this.text);
        variables.put("article",this.article);
        variables.put("link",this.link + uuid);
        variables.put("linkMsg",this.linkMsg);
        return variables;
    }
}
