package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, START_SEQ);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, START_SEQ), newMeal);
    }

    @Test
    public void duplicateMealCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Duplicate", 500), START_SEQ));
    }

    @Test
    public void delete() throws Exception {
        service.delete(meal1.getId(), START_SEQ);
        assertThrows(NotFoundException.class, () -> service.get(meal1.getId(), START_SEQ));
    }

    @Test
    public void deleteAlienMeal() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(meal1.getId(), START_SEQ + 1));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(10, START_SEQ));
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(meal1.getId(), START_SEQ);
        assertMatch(meal, meal1);
    }

    @Test
    public void getAlienMeal() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(meal1.getId(), START_SEQ + 1));
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(10, START_SEQ));
    }

    @Test
    public void getFiltered() throws Exception {
        List<Meal> meals = service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 30), LocalDate.of(2020, Month.JANUARY, 31), START_SEQ);
        assertMatch(meals, meal3, meal2, meal1);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, START_SEQ);
        assertMatch(service.get(meal1.getId(), START_SEQ), updated);
    }

    @Test
    public void updateAlienMeal() throws Exception {
        assertThrows(NotFoundException.class, () -> service.update(meal1, START_SEQ + 1));
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(START_SEQ);
        assertMatch(all, meal3, meal2, meal1);
    }
}