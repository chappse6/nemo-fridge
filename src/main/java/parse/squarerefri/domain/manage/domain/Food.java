package parse.squarerefri.domain.manage.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Food {

    @Id
    @Column(name = "food_type")
    private String id;

    private String foodTypeEn;

    private int judgeUsdFridge;

    private int judgeUsdFrozen;

    public Food() {

    }
}
