package parse.squarerefri.domain.manage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import parse.squarerefri.domain.manage.domain.Food;
import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.manage.domain.StorageStatus;
import parse.squarerefri.domain.manage.model.ManageInput;
import parse.squarerefri.domain.manage.repository.FoodRepository;
import parse.squarerefri.domain.manage.repository.ManageRepository;
import parse.squarerefri.domain.member.domain.Member;
import parse.squarerefri.domain.member.model.MemberInput;
import parse.squarerefri.domain.member.repository.MemberRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManageServiceImplTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    ManageRepository manageRepository;
    @Mock
    FoodRepository foodRepository;

    @InjectMocks
    ManageServiceImpl manageService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void 제품관리_등록() {
        //given
        when(memberRepository.findById(anyString())).thenReturn(Optional.of(Member.createMember(
                MemberInput.builder().userId("test").userName("testName").password("pwd").build(),
                "test", "test")));
        when(foodRepository.findByFood(anyString())).thenReturn(Optional.of(Food.builder().id("testFood").judgeUsdFridge(10).judgeUsdFrozen(15).build()));

        ManageInput manageInput = ManageInput.builder()
                .foodName("testFoodName").foodType("testFood").memberId("test").sellbydate(LocalDate.now().plusDays(5)).build();
        //when
        String foodName = manageService.registManage(manageInput);

        //then
        assertThat(foodName).isNotNull();

        verify(memberRepository).findById(anyString());
        verify(foodRepository).findByFood(anyString());
        verify(manageRepository).save(any(Management.class));
    }

    @Test
    void 제품관리_전체조회() {
        //given
        String memberId = "testId";
        StorageStatus storageStatus = StorageStatus.FREEZER;
        when(manageRepository.findAll(anyString(),any(StorageStatus.class))).thenReturn(Collections.singletonList(Management.createManage(
                Member.createMember(MemberInput.builder().userId("test").userName("testName").password("pwd").build(), "test", "test"),
                Food.builder().id("testFood").judgeUsdFridge(10).judgeUsdFrozen(15).build(),
                ManageInput.builder()
                        .foodName("testFoodName").foodType("testFood").memberId("test").sellbydate(LocalDate.now().plusDays(5)).build()
        )));
        //when
        List<Management> managements = manageService.findAll(memberId, storageStatus);

        //then
        assertThat(managements).filteredOn(o -> o.getMember().getId().contains(memberId));
    }

    @Test
    void 제품관리_전체조회리스트() {
        //given
        String foodType = "testType";
        when(foodRepository.findAll()).thenReturn(Collections.singletonList(Food.builder().id(foodType).judgeUsdFridge(15).judgeUsdFrozen(20).build()));
        //when
        List<String> foodList = manageService.findAllForList();
        //then
        assertThat(foodList).contains(foodType);
    }

    @Test
    void 제품관리_삭제() {
        //given
        when(manageRepository.findOne(anyLong())).thenReturn(Management.createManage( Member.createMember(MemberInput.builder().userId("test").userName("testName").password("pwd").build(), "test", "test"),
                Food.builder().id("testFood").judgeUsdFridge(10).judgeUsdFrozen(15).build(),
                ManageInput.builder()
                        .foodName("testFoodName").foodType("testFood").memberId("test").sellbydate(LocalDate.now().plusDays(5)).build()
        ));
        //when
        manageService.deleteManage(1L);

        //then
        verify(manageRepository).findOne(anyLong());
    }
}