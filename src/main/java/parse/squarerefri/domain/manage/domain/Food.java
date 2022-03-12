package parse.squarerefri.domain.manage.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Food {

    @Id
    @Column(name = "food_type")
    private String id;

    @OneToOne(mappedBy = "food", fetch = FetchType.LAZY)
    private Management management;

    private int judgeUsdFridge;

    private int judgeUsdFrozen;

}
