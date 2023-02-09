package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepositoryInMemory implements CrudRepository<Meal> {

    private final AtomicInteger counter;
    private final Map<Integer, Meal> meals;

    public MealRepositoryInMemory() {
        counter = new AtomicInteger();
        meals = new ConcurrentHashMap<>();
        List<Meal> testMeals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        for (Meal meal : testMeals) {
            create(meal);
        }
    }

    @Override
    public Meal findById(int id) {
        return meals.get(id);
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal create(Meal meal) {
        return meals.put(counter.incrementAndGet(), new Meal(counter.get(), meal.getDateTime(), meal.getDescription(), meal.getCalories()));
    }

    @Override
    public Meal update(Meal meal) {
        meals.replace(meal.getId(), meal);
        return findById(meal.getId());
    }

    @Override
    public boolean delete(int id) {
        return meals.remove(id) != null;
    }
}
