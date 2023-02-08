package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepository implements CrudRepository<Meal> {

    private final AtomicInteger counter = new AtomicInteger();

    private final Map<Integer, Meal> meals;

    public MealRepository() {
        meals = new ConcurrentHashMap<>();
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public Meal findById(int id) {
        return null;
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal create(Meal meal) {
        meal.setId(counter.incrementAndGet());
        meals.put(counter.get(), meal);
        return meal;
    }

    @Override
    public Meal update(Meal meal) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
