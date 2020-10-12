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

//            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
//
//            mealRestController.create(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 1, 10, 0), "Завтрак1", 500));
//            mealRestController.getAll().forEach(System.out::println);
//
//            System.out.println(mealRestController.get(3));
//
//            mealRestController.delete(6);
//            mealRestController.getAll().forEach(System.out::println);
//
//            mealRestController.update(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 2, 10, 0), "Завтрак11", 500), 5);
//            mealRestController.getAll().forEach(System.out::println);
//
//            mealRestController.getFiltered(LocalDate.of(2020, Month.JANUARY, 30), LocalTime.MIN, LocalDate.of(2020, Month.JANUARY, 31), LocalTime.MAX)
//            .forEach(System.out::println);

        }
    }
}
