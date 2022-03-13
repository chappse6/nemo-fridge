package parse.squarerefri.domain.manage.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parse.squarerefri.domain.manage.domain.Food;
import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.manage.domain.StorageStatus;
import parse.squarerefri.domain.manage.repository.ManageRepository;
import parse.squarerefri.domain.member.domain.Member;
import parse.squarerefri.domain.member.repository.MemberRepository;

import java.time.LocalDate;
import java.util.List;

import static parse.squarerefri.domain.manage.domain.Management.createManage;

@Service
@Transactional
@RequiredArgsConstructor
public class ManageServiceImpl implements ManageService{

    MemberRepository memberRepository;
    ManageRepository manageRepository;

    @Transactional
    @Override
    public String registManage(String memberId, String foodType,String foodName, LocalDate sellbydate
            , StorageStatus storageStatus) {

        Member member = memberRepository.findOne(memberId);
        Food food = manageRepository.findOneInFood(foodType);

        Management management = Management.createManage(member, foodName, food, sellbydate, storageStatus);
        management.mathUseByDate();
        management.checkStatus();

        manageRepository.save(management);

        return management.getFoodName();
    }

    @Transactional
    @Override
    public List<Management> findAll(String memberId, String foodType, StorageStatus storageStatus) {
        List<Management> managements = manageRepository.findAll(memberId, foodType, storageStatus);
        for(Management management : managements) {
            management.checkStatus();
        }
        return managements;
    }
}
