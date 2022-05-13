package parse.squarerefri.domain.manage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parse.squarerefri.domain.manage.domain.Food;
import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.manage.domain.StorageStatus;
import parse.squarerefri.domain.manage.exception.FoodException;
import parse.squarerefri.domain.manage.model.ManageInput;
import parse.squarerefri.domain.manage.repository.FoodRepository;
import parse.squarerefri.domain.manage.repository.ManageRepository;
import parse.squarerefri.domain.member.domain.Member;
import parse.squarerefri.domain.member.exception.MemberException;
import parse.squarerefri.domain.member.exception.MemberExceptionCode;
import parse.squarerefri.domain.member.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

import static parse.squarerefri.domain.manage.exception.FoodExceptionCode.NOT_FOUND_FOOD;
import static parse.squarerefri.domain.member.exception.MemberExceptionCode.MEMBER_USERNAME_NOT_FOUND;

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
                .orElseThrow(() -> new MemberException(MEMBER_USERNAME_NOT_FOUND));
        Food food = foodRepository.findByFood(parameter.getFoodType())
                .orElseThrow(() -> new FoodException(NOT_FOUND_FOOD));

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
