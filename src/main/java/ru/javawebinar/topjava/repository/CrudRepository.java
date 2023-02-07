package ru.javawebinar.topjava.repository;

import java.util.List;

public interface CrudRepository<T> {
    List<T> findAll();
    T findById(int id);
    T create(T t);
    T update(T t);
    void delete(int id);
}
