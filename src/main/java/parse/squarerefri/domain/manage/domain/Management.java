package parse.squarerefri.domain.manage.domain;

import lombok.Getter;
import lombok.Setter;
import parse.squarerefri.domain.member.domain.Member;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private LocalDate sellbydate;

    private LocalDate usebydate;

    @Column(columnDefinition = "char(1) default '0'")
    private Character delFlag;

    @Enumerated(EnumType.STRING)
    private ManageStatus status; // 식품상태 [GOOD, FINE, BAD]

    @Enumerated(EnumType.STRING)
    private StorageStatus storageStatus; // 보관상태 [FRIDGE, FROZEN]

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getManagement().add(this);
    }

    public void setFood(Food food) {
        this.food = food;
        food.setManagement(this);
    }

    //==생성 메서드==//
    public static Management createManage(Member member, String foodName, Food food, LocalDate sellbydate
            , StorageStatus storageStatus) {
        Management manage = new Management();
        manage.setMember(member);
        manage.setFoodName(foodName);
        manage.setFood(food);
        manage.setSellbydate(sellbydate);
        manage.setStorageStatus(storageStatus);

        return manage;
    }

    //==비지니스 로직==//
    //등록 취소
    public void cancel() {
        this.setDelFlag('1');
    }

    //소비기한 계산
    public void mathUseByDate() {
        if (storageStatus == StorageStatus.FRIDGE) {
            this.setUsebydate(LocalDate.now().plusDays(food.getJudgeUsdFridge()));
        } else if (storageStatus == StorageStatus.FROZEN) {
            this.setUsebydate(LocalDate.now().plusDays(food.getJudgeUsdFrozen()));
        }
    }

    //상품 상태 체크
    public void checkStatus() {
        if (LocalDate.now().isBefore(usebydate)) {
            this.setStatus(ManageStatus.GOOD);
        } else if (LocalDate.now().isBefore(sellbydate)) {
            this.setStatus(ManageStatus.FINE);
        } else {
            this.setStatus(ManageStatus.BAD);
        }
    }


}
