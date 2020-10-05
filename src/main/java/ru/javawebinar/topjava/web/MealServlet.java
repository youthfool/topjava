package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MemoryMealService;
import ru.javawebinar.topjava.service.Service;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final String LIST_MEALS = "/meals.jsp";
    private final Service<Meal> mealService = new MemoryMealService();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            forward = LIST_MEALS;
            List<MealTo> mealTos = MealsUtil.filteredByStreams(mealService.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("mealsList", mealTos);
        } else if (action.equalsIgnoreCase("delete")) {
            forward = LIST_MEALS;
            Long mealId = Long.parseLong(request.getParameter("mealId"));
            mealService.delete(mealId);
            List<MealTo> mealTos = MealsUtil.filteredByStreams(mealService.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("mealsList", mealTos);
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            Long mealId = Long.parseLong(request.getParameter("mealId"));
            Meal meal = mealService.getById(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("insert")) {
            forward = INSERT_OR_EDIT;
        } else {
            forward = LIST_MEALS;
            List<MealTo> mealTos = MealsUtil.filteredByStreams(mealService.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("mealsList", mealTos);
        }
        log.debug("forward to " + forward);
        request.setAttribute("dateTimeFormatter", formatter);
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));
        String mealId = req.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()) {
            mealService.save(meal);
        } else {
            mealService.update(meal, Long.parseLong(mealId));
        }

        List<MealTo> mealTos = MealsUtil.filteredByStreams(mealService.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        req.setAttribute("mealsList", mealTos);
        req.setAttribute("dateTimeFormatter", formatter);
        req.getRequestDispatcher(LIST_MEALS).forward(req, resp);
    }
}
