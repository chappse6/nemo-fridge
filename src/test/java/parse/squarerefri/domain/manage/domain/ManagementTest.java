package parse.squarerefri.domain.manage.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ManagementTest {

    @Test
    void 소비기한계산_냉장() {
        //given
        Food food = new Food();
        food.setJudgeUsdFridge(10);

        Management management = new Management();
        management.setStorageStatus(StorageStatus.FRIDGE);
        management.setFood(food);

        //when
        management.mathUseByDate();

        //then
        assertThat(management.getUsebydate()).isEqualTo(LocalDate.now().plusDays(10));
    }

    @Test
    void 소비기한계산_냉동() {
        //given
        Food food = new Food();
        food.setJudgeUsdFrozen(15);

        Management management = new Management();
        management.setStorageStatus(StorageStatus.FREEZER);
        management.setFood(food);

        //when
        management.mathUseByDate();
        System.out.println(management.getUsebydate());

        //then
        assertThat(management.getUsebydate()).isEqualTo(LocalDate.now().plusDays(15));
    }

    @Test
    void 제품상태확인_정상() {
        //given
        Management management = new Management();
        management.setSellbydate(LocalDate.now().plusDays(5));
        management.setUsebydate(LocalDate.now().plusDays(15));

        //when
        management.checkStatus();
        System.out.println(management.getStatus());

        //then
        assertThat(management.getStatus()).isEqualTo(ManageStatus.GOOD);
    }

    @Test
    void 제품상태확인_조심() {
        //given
        Management management = new Management();
        management.setSellbydate(LocalDate.now().minusDays(1));
        management.setUsebydate(LocalDate.now().plusDays(1));

        //when
        management.checkStatus();
        System.out.println(management.getStatus());

        //then
        assertThat(management.getStatus()).isEqualTo(ManageStatus.FINE);
    }

    @Test
    void 제품상태확인_위험() {
        //given
        Management management = new Management();
        management.setSellbydate(LocalDate.now().minusDays(3));
        management.setUsebydate(LocalDate.now().minusDays(1));

        //when
        management.checkStatus();
        System.out.println(management.getStatus());

        //then
        assertThat(management.getStatus()).isEqualTo(ManageStatus.BAD);
    }

    @Test
    void 제품상태확인_정상_당일() {
        //given
        Management management = new Management();
        management.setSellbydate(LocalDate.now());
        management.setUsebydate(LocalDate.now().plusDays(1));

        //when
        management.checkStatus();
        System.out.println(management.getStatus());

        //then
        assertThat(management.getStatus()).isEqualTo(ManageStatus.GOOD);
    }

    @Test
    void 제품상태확인_소비기한_null() {
        //given
        Management management = new Management();
        management.setSellbydate(LocalDate.now().minusDays(1));

        //when
        management.checkStatus();
        System.out.println(management.getStatus());

        //then
        assertThat(management.getStatus()).isEqualTo(ManageStatus.BAD);
    }
}