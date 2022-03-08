package parse.squarerefri.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @Column(name = "member_id")
    private String id;

    private String password;

    private String memberName;

    private String email;

    private int phoneNumber;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(columnDefinition = "char(1) default '1'")
    private Character delFlag;

    @OneToMany(mappedBy = "member")
    private List<Management> management = new ArrayList<>();

}
