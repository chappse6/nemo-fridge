package parse.squarerefri.domain.manage.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import parse.squarerefri.domain.manage.domain.Food;
import parse.squarerefri.domain.manage.domain.Management;
import parse.squarerefri.domain.manage.domain.StorageStatus;

import javax.persistence.EntityManager;
import java.util.List;

@Component
@Repository
@RequiredArgsConstructor
public class ManageRepositoryImpl implements ManageRepository {

    private final EntityManager em;

    @Override
    public void save(Management management) {
        em.persist(management);
    }

    @Override
    public Management findOne(Long id) {
        return em.find(Management.class, id);
    }

    @Override
    public List<Management> findAll(String memberId, StorageStatus storageStatus) {
        return em.createQuery("select a from Management as a where" +
                        " a.member = :memberId and" +
                        " a.storageStatus = :storageStatus and" +
                        " a.delFlag = '0'", Management.class)
                .setParameter("memberId", memberId)
                .setParameter("storageStatus", storageStatus)
                .getResultList();
    }

    @Override
    public Food findOneInFood(String id) {
        return em.find(Food.class, id);
    }
}
