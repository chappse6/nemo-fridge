package parse.squarerefri.domain.member.model;

import groovy.transform.ToString;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class ResetPasswordInput {
    private String userId;
    private String userName;

    private String id;
    private String password;
}
