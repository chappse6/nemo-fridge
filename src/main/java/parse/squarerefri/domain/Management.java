package parse.squarerefri.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Management {

    @Id
    @GeneratedValue
    @Column(name = "manage_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String foodName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_type")
    private Food food;

    private Date sellbydate;

    private Date usebydate;

    @Column(columnDefinition = "char(1) default '1'")
    private Character delFlag;

    @Enumerated(EnumType.STRING)
    private ManageStatus status; // 식품상태 [GOOD, FINE, BAD]
}
