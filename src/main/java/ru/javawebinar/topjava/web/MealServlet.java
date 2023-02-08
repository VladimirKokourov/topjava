package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.CrudRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final LocalTime START_TIME = LocalTime.MIN;
    private static final LocalTime END_TIME = LocalTime.MAX;
    private static final int CALORIES_PER_DAY = 2000;

    private static final String INSERT = "/add-meal.jsp";
    private static final String EDIT = "/edit-meal.jsp";
    private static final String LIST_MEAL = "/meals.jsp";

    private CrudRepository<Meal> repository;

    @Override
    public void init() {
        repository = new MealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String forward="";
        String action = request.getParameter("action");
        if (action == null) {
            forward = LIST_MEAL;
            request.setAttribute("meals", MealsUtil.filteredByStreams(repository.findAll(), START_TIME, END_TIME, CALORIES_PER_DAY));
        } else if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            repository.delete(mealId);
            forward = LIST_MEAL;
            request.setAttribute("meals", MealsUtil.filteredByStreams(repository.findAll(), START_TIME, END_TIME, CALORIES_PER_DAY));
        } else if (action.equalsIgnoreCase("insert")) {
            forward = INSERT;
        } else if (action.equalsIgnoreCase("edit")) {
            request.setAttribute("meal", repository.findById(Integer.parseInt(request.getParameter("mealId"))));
            forward = EDIT;
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("localDateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal;
        if (request.getParameter("mealId") != null) {
           meal = new Meal(Integer.parseInt(request.getParameter("mealId")), localDateTime, description, calories);
           repository.update(meal);
        } else {
            meal = new Meal(localDateTime, description, calories);
            repository.create(meal);
        }

        request.setAttribute("meals", MealsUtil.filteredByStreams(repository.findAll(), START_TIME, END_TIME, CALORIES_PER_DAY));
        request.getRequestDispatcher(LIST_MEAL).forward(request, response);
    }
}
