package parse.squarerefri.domain.member.model;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Getter
@Setter
public class ResetPasswordInput {
    private String userId;
    private String userName;
}
