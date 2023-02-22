package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID_1 = START_SEQ + 3;
    public static final int MEAL_ID_2 = START_SEQ + 4;
    public static final int MEAL_ID_3 = START_SEQ + 5;
    public static final int MEAL_ID_4 = START_SEQ + 6;
    public static final int MEAL_ID_5 = START_SEQ + 7;
    public static final int MEAL_ID_6 = START_SEQ + 8;
    public static final int MEAL_ID_7 = START_SEQ + 9;
    public static final int ADMINS_MEAL_ID = START_SEQ + 10;
    public static final int NOT_FOUND_MEAL_ID = START_SEQ + 11;

    public static final LocalDate LOCAL_DATE = LocalDate.of(2020, Month.JANUARY, 30);

    public static final Meal meal1 = new Meal(MEAL_ID_1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(MEAL_ID_2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(MEAL_ID_3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal meal4 = new Meal(MEAL_ID_4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(MEAL_ID_5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal meal6 = new Meal(MEAL_ID_6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal meal7 = new Meal(MEAL_ID_7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal adminsMeal = new Meal(ADMINS_MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак Админа", 500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "New Meal", 1999);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(adminsMeal);
        updated.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 10));
        updated.setDescription("Updated");
        updated.setCalories(2018);
        return updated;
    }

    public static Meal getNotExistMeal() {
        Meal notExist = new Meal(adminsMeal);
        notExist.setId(NOT_FOUND_MEAL_ID);
        return notExist;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
