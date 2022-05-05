package parse.squarerefri.domain.manage.service;

import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.manage.domain.StorageStatus;
import parse.squarerefri.domain.manage.model.ManageInput;

import java.time.LocalDate;
import java.util.List;

public interface ManageService {

    /**
     * 식품관리 등록
     */
    String registManage(ManageInput parameter);

    /**
     * 식품관리 전체 조회
     */
    List<Management> findAll(String memberId, StorageStatus storageStatus);

    /**
     * 제품 종류 리스트를 위한 식품관리 전체 조회
     */
    List<String> findAllForList();

    /**
     * 식품관리 삭제
     */
    void deleteManage(Long id);
}
