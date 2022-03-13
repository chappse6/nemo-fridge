package parse.squarerefri.domain.manage.service;

import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.manage.domain.StorageStatus;

import java.time.LocalDate;
import java.util.List;

public interface ManageService {

    String registManage(String memberId, String foodType,String foodName, LocalDate sellbydate
            , StorageStatus storageStatus);

    List<Management> findAll(String memberId, String foodType, StorageStatus storageStatus);
}
