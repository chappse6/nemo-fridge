package parse.squarerefri.domain.manage.repository;

import parse.squarerefri.domain.manage.domain.Food;

import java.util.List;
import java.util.Optional;

public interface FoodRepository {

    Optional<Food> findByFood(String id);

    List<Food> findAll();
}
