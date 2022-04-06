package parse.squarerefri.domain.manage.service;

import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.manage.domain.StorageStatus;
import parse.squarerefri.domain.manage.model.ManageInput;

import java.time.LocalDate;
import java.util.List;

public interface ManageService {

    String registManage(ManageInput parameter);

    List<Management> findAll(String memberId, StorageStatus storageStatus);

    List<String> findAllForList();

    void deleteManage(Long id);
}
