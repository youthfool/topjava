package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryMealService implements Service<Meal> {
    private static final Map<Long, Meal> meals = new ConcurrentHashMap<Long, Meal>() {{
        Meal meal = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        meal.setId(0L);
        put(0L, meal);
        meal = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        meal.setId(1L);
        put(1L, meal);
        meal = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        meal.setId(2L);
        put(2L, meal);
        meal = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
        meal.setId(3L);
        put(3L, meal);
        meal = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        meal.setId(4L);
        put(4L, meal);
        meal = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        meal.setId(5L);
        put(5L, meal);
        meal = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
        meal.setId(6L);
        put(6L, meal);
    }};

    private static final AtomicLong MEALS_ID_HOLDER = new AtomicLong(7L);

    @Override
    public Meal getById(Long id) {
        return meals.get(id);
    }

    @Override
    public void save(Meal meal) {
        final Long mealId = MEALS_ID_HOLDER.getAndIncrement();
        meal.setId(mealId);
        meals.put(mealId, meal);
    }

    @Override
    public boolean delete(Long id) {
        return meals.remove(id) != null;
    }

    @Override
    public boolean update(Meal meal, Long id) {
        if (meals.containsKey(id)) {
            meal.setId(id);
            meals.put(id, meal);
            return true;
        }
        return false;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }
}
