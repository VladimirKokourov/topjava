package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAll().forEach(System.out::println);

            Meal meal = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 16, 0), "Полдник", 100);
            mealRestController.create(meal);
            System.out.println(mealRestController.get(8));

            Meal updateMeal = new Meal(8,1, LocalDateTime.of(2020, Month.JANUARY, 31, 22, 0), "Сонник", 100);
            mealRestController.update(updateMeal, 8);
            System.out.println(mealRestController.get(8));

            mealRestController.delete(8);
            mealRestController.getAll().forEach(System.out::println);
        }
    }
}
