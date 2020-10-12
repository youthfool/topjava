package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) { this.repository = repository; }

    public Meal create(Meal meal, Integer userId) { return repository.save(meal, userId); }

    public void delete(int id, Integer userId) {
        MealsUtil.checkUserAccess(repository.get(id), userId);
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Meal get(int id, Integer userId) {
        Meal meal = checkNotFoundWithId(repository.get(id), id);
        MealsUtil.checkUserAccess(meal, userId);
        return meal;
    }

    public Collection<Meal> getAll(Integer userId) {
        return repository.getAll(userId);
    }

    public void update(Meal meal, Integer userId) {
        MealsUtil.checkUserAccess(repository.get(meal.getId()), userId);
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public Collection<Meal> getFiltered(Integer userId, LocalDate startDate,
                                        LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return repository.getFiltered(userId, startDate, startTime, endDate, endTime);
    }
}