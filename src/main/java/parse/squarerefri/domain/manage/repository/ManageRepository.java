package parse.squarerefri.domain.manage.repository;

import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.manage.domain.StorageStatus;

import java.util.List;

public interface ManageRepository {

    void save(Management management);

    Management findOne(Long id);

    List<Management> findAll(String memberId, StorageStatus storageStatus);

}
