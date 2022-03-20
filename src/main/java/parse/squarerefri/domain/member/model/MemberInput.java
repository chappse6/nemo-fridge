package parse.squarerefri.domain.member.model;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Getter
@Setter
public class MemberInput {
    private String userId;
    private String userName;
    private String phone;
    private String password;
}
