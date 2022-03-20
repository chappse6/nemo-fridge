package parse.squarerefri.domain.manage.repository;

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
    void  하나찾기() {
        //given
        Long id = 1L;

        //when
        Management management = manageRepository.findOne(id);

        //then
        assertThat(management.getId()).isEqualTo(1L);
    }
    @Test
    void 항목열기() {
        //given
        StorageStatus storageStatus = FRIDGE;
        Member member = new Member();
        member.setId("test");

        //when
        List<Management> managementList = manageRepository.findAll(member.getId(), storageStatus);

        for (Management manage : managementList) {
            System.out.println(manage.toString());
        }

        //then
        assertThat(managementList.size() >= 1);

    }
}