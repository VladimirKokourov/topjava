package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    public static final Integer TEST_USER_ID = 1;

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, TEST_USER_ID));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }

        if (isMealOfAuthUser(meal.getId(), userId)) {
            meal.setUserId(userId);
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        if (isMealOfAuthUser(id, userId)) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        return isMealOfAuthUser(id, userId) ? repository.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> isMealOfAuthUser(meal.getId(), userId))
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
    }

    private boolean isMealOfAuthUser(int id, int userId) {
        if (repository.get(id) == null) {
            return false;
        }
        return repository.get(id).getUserId() == userId;
    }
}

