package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;


@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }

    public List<MealTo> getAll(String startDateStr, String startTimeStr,
                                        String endDateStr, String endTimeStr) {
        LocalDate startDate;
        LocalTime startTime;
        LocalDate endDate;
        LocalTime endTime;

        if (startTimeStr == null || startTimeStr.isEmpty()) {
            startTime = LocalTime.MIN;
        } else {
            startTime = LocalTime.parse(startTimeStr);
        }
        if (endTimeStr == null || endTimeStr.isEmpty()) {
            endTime = LocalTime.MAX;
        } else {
            endTime = LocalTime.parse(endTimeStr);
        }
        if (startDateStr == null || startDateStr.isEmpty()) {
            startDate = LocalDate.MIN;
        } else {
            startDate = LocalDate.parse(startDateStr);
        }
        if (endDateStr == null || endDateStr.isEmpty()) {
            endDate = LocalDate.MAX;
        } else {
            endDate = LocalDate.parse(endDateStr);
        }
        log.info("getFiltered startDate={}, startTime={}, endDate={}, endTime={}",
                startDate, startTime, endDate, endTime);
        return MealsUtil.getTos(service.getFiltered(SecurityUtil.authUserId(), startDate, startTime, endDate, endTime),
                SecurityUtil.authUserCaloriesPerDay());
    }

}