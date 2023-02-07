package ru.javawebinar.topjava.model;

import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealRepository implements CrudRepository<Meal> {
    private static final Logger log = getLogger(MealRepository.class);
    private static final AtomicInteger COUNTER = new AtomicInteger();

    private final List<Meal> meals;

    public MealRepository() {
        meals = new CopyOnWriteArrayList<>();
        meals.add(new Meal(COUNTER.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(COUNTER.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(COUNTER.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(COUNTER.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(COUNTER.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(COUNTER.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(COUNTER.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    private Meal findByID(int id) {
        return meals.stream()
                .filter(el -> el.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("not found meal with id: " + id));
    }

    @Override
    public List<Meal> findAll() {
        return meals;
    }

    @Override
    public void create(Meal meal) {
        meal.setId(COUNTER.incrementAndGet());
        meals.add(meal);
    }

    @Override
    public void update(Meal meal) {
        Meal oldMeal = findByID(meal.getId());
        meals.remove(oldMeal);
        meals.add(meal);
    }

    @Override
    public void delete(int id) {
        Meal meal = findByID(id);
        meals.remove(meal);
    }
}
