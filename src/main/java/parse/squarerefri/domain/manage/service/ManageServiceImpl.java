package parse.squarerefri.domain.manage.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.manage.domain.StorageStatus;
import parse.squarerefri.domain.manage.repository.ManageRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ManageServiceImpl implements ManageService{

    ManageRepository manageRepository;

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
