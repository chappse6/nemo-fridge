package parse.squarerefri.domain.manage.service;

import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.manage.domain.StorageStatus;
import parse.squarerefri.domain.manage.model.ManageInput;

import java.time.LocalDate;
import java.util.List;

public interface ManageService {

    String registManage(ManageInput parameter, StorageStatus storageStatus);

    List<Management> findAll(String memberId, StorageStatus storageStatus);
}
