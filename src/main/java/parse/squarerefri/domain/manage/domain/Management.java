package parse.squarerefri.domain.manage.domain;

import lombok.Getter;
import lombok.Setter;
import parse.squarerefri.domain.manage.model.ManageInput;
import parse.squarerefri.domain.member.domain.Member;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Management {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manage_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private String foodName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_type")
    private Food food;

    private LocalDate sellbydate;

    private LocalDate usebydate;

    @Column(columnDefinition = "char(1) default '0'")
    private Character delFlag;

    @Enumerated(EnumType.STRING)
    private ManageStatus status; // 식품상태 [GOOD, FINE, BAD]

    @Enumerated(EnumType.STRING)
    private StorageStatus storageStatus; // 보관상태 [FRIDGE, FREEZER]

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getManagement().add(this);
    }

    //==생성 메서드==//
    public static Management createManage(Member member, Food food, ManageInput parameter) {
        Management manage = new Management();
        manage.setMember(member);
        manage.setFoodName(parameter.getFoodName());
        manage.setFood(food);
        manage.setSellbydate(parameter.getSellbydate());
        manage.setDelFlag('0');
        manage.setStorageStatus(parameter.getStorageStatus());

        return manage;
    }

    //==비지니스 로직==//
    //등록 삭제
    public void delete() {
        this.setDelFlag('1');
    }

    //소비기한 계산
    public void mathUseByDate() {
        if (storageStatus == StorageStatus.FRIDGE) {
            this.setUsebydate(LocalDate.now().plusDays(food.getJudgeUsdFridge()));
        } else if (storageStatus == StorageStatus.FREEZER) {
            this.setUsebydate(LocalDate.now().plusDays(food.getJudgeUsdFrozen()));
        }
    }

    //상품 상태 체크
    public void checkStatus() {

        if (LocalDate.now().isBefore(sellbydate) || LocalDate.now().isEqual(sellbydate)) {
            this.setStatus(ManageStatus.GOOD);
        } else {
            this.setStatus(ManageStatus.BAD);
        }

        if (usebydate != null) {
            if (LocalDate.now().isBefore(sellbydate) || LocalDate.now().isEqual(sellbydate)) {
                this.setStatus(ManageStatus.GOOD);
            } else if (LocalDate.now().isBefore(usebydate) || LocalDate.now().isEqual(usebydate)) {
                this.setStatus(ManageStatus.FINE);
            } else {
                this.setStatus(ManageStatus.BAD);
            }
        }
    }
}
