package parse.squarerefri.domain.manage.service;

import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.manage.domain.StorageStatus;

import java.util.List;

public interface ManageService {

    List<Management> findAll(String memberId, String foodType, StorageStatus storageStatus);
}
