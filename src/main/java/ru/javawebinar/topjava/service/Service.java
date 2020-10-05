package ru.javawebinar.topjava.service;

import java.util.List;

public interface Service<T> {
    T getById(Long id);

    void save(T object);

    boolean delete(Long id);

    boolean update(T object, Long id);

    List<T> getAll();
}
