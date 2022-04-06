package parse.squarerefri.domain.manage.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import parse.squarerefri.domain.manage.domain.Food;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Repository
public class FoodRepositoryImpl implements FoodRepository{

    private final EntityManager em;

    @Override
    public Optional<Food> findByFood(String id) {
        Food food = em.find(Food.class, id);
        return Optional.ofNullable(food);
    }

    @Override
    public List<Food> findAll() {
        return em.createQuery("select f from Food f", Food.class)
                .getResultList();
    }
}
