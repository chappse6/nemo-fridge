package parse.squarerefri.domain.manage.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parse.squarerefri.domain.manage.domain.Food;
import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.manage.domain.StorageStatus;
import parse.squarerefri.domain.manage.exception.FoodNullException;
import parse.squarerefri.domain.manage.model.ManageInput;
import parse.squarerefri.domain.manage.repository.FoodRepository;
import parse.squarerefri.domain.manage.repository.ManageRepository;
import parse.squarerefri.domain.member.domain.Member;
import parse.squarerefri.domain.member.repository.MemberRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static parse.squarerefri.domain.manage.domain.Management.createManage;

@Service
@Transactional
@RequiredArgsConstructor
public class ManageServiceImpl implements ManageService {

    private final MemberRepository memberRepository;
    private final ManageRepository manageRepository;
    private final FoodRepository foodRepository;

    @Transactional
    @Override
    public String registManage(ManageInput parameter) {

        Member member = memberRepository.findById(parameter.getMemberId())
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다."));
        Food food = foodRepository.findByFood(parameter.getFoodType())
                .orElseThrow(() -> new FoodNullException("제품 정보가 존재하지 않습니다."));

        Management management = Management.createManage(member, food, parameter);
        management.mathUseByDate();
        management.checkStatus();

        manageRepository.save(management);

        return management.getFoodName();
    }

    @Transactional
    @Override
    public List<Management> findAll(String memberId, StorageStatus storageStatus) {
        List<Management> managements = manageRepository.findAll(memberId, storageStatus);
        for (Management management : managements) {
            management.checkStatus();
        }
        return managements;
    }

    @Transactional
    @Override
    public List<String> findAllForList() {
        List<Food> foods = foodRepository.findAll();
        List<String> foodlist = new ArrayList<>();
        for(Food food : foods) {
            foodlist.add(food.getId());
        }
        return foodlist;
    }

    @Transactional
    @Override
    public void deleteManage(Long id) {
        Management management = manageRepository.findOne(id);
        management.delete();
    }
}
