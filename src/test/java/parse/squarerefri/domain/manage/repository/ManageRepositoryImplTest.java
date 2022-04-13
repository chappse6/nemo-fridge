package parse.squarerefri.domain.manage.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.manage.domain.StorageStatus;
import parse.squarerefri.domain.member.domain.Member;
import parse.squarerefri.domain.member.model.MemberInput;
import parse.squarerefri.domain.member.repository.MemberRepository;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static parse.squarerefri.domain.manage.domain.StorageStatus.FRIDGE;

@SpringBootTest
@Transactional
class ManageRepositoryImplTest {

    @Autowired
    ManageRepository manageRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 항목열기() {
        //given
        String foodName = "김치찌개";
        Management management1 = new Management();
        management1.setFoodName(foodName);

        StorageStatus storageStatus = FRIDGE;
        Member member = new Member();
        member.setId("test");
        member.setEmailAuthFlag(false);
        management1.setStorageStatus(storageStatus);
        management1.setMember(member);
        manageRepository.save(management1);

        //when
        List<Management> managementList = manageRepository.findAll(member.getId(), storageStatus);

        //then
        assertThat(managementList.size() >= 1);

    }
}