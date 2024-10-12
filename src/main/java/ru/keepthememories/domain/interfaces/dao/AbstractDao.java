package ru.keepthememories.domain.interfaces.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T> {

    Integer add(T item);

    @SuppressWarnings("unused")
    void add(List<T> list);

    void delete(Integer id);

    void update(Integer id, T item);

    Optional<T> getById(Integer id);

    List<T> getAll();

    List<T> getRange(Long limit, Long offset);

}
