package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal, Integer userId);

    // false if not found
    boolean delete(int id);

    // null if not found
    Meal get(int id);

    Collection<Meal> getAll(Integer userId);

    Collection<Meal> getFiltered(Integer userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime);
}
