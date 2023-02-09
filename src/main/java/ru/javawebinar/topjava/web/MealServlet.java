package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.CrudRepository;
import ru.javawebinar.topjava.repository.MealRepositoryInMemory;
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
    private static final int CALORIES_PER_DAY = 2000;

    private static final String INSERT = "/add-meal.jsp";
    private static final String EDIT = "/edit-meal.jsp";
    private static final String LIST_MEAL = "/meals.jsp";

    private CrudRepository<Meal> repository;

    @Override
    public void init() {
        repository = new MealRepositoryInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.filteredByStreams(repository.findAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            request.getRequestDispatcher(LIST_MEAL).forward(request, response);
        } else {
            switch (action) {
                case "delete": {
                    int mealId = getParseInt(request, "mealId");
                    repository.delete(mealId);
                    response.sendRedirect("meals");
                }
                break;
                case "insert": {
                    request.getRequestDispatcher(INSERT).forward(request, response);
                }
                break;
                case "edit": {
                    request.setAttribute("meal", repository.findById(getParseInt(request, "mealId")));
                    request.getRequestDispatcher(EDIT).forward(request, response);
                }
                break;
                default: {
                    response.sendRedirect("meals");
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("localDateTime"));
        String description = request.getParameter("description");
        int calories = getParseInt(request, "calories");

        if (request.getParameter("mealId") != null) {
            Meal meal = new Meal(getParseInt(request, "mealId"), localDateTime, description, calories);
            repository.update(meal);
        } else {
            Meal meal = new Meal(localDateTime, description, calories);
            repository.create(meal);
        }

        response.sendRedirect("meals");
    }

    private int getParseInt(HttpServletRequest request, String mealId) {
        return Integer.parseInt(request.getParameter(mealId));
    }
}
