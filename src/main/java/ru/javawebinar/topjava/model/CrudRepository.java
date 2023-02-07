package ru.javawebinar.topjava.model;

import java.util.List;

public interface CrudRepository<T> {
    List<T> findAll();
    void create(T t);
    void update(T t);
    void delete(int id);
}
