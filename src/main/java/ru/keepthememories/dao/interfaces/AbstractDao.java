package ru.keepthememories.dao.interfaces;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T> {

    Integer add(T item);

    @SuppressWarnings("unused")
    void add(List<T> list);

    @SuppressWarnings("unused")
    void delete(Integer id);

    /**
     * Обновляет только по доступным полям item
     *
     * @param id индектификатор сущности
     * @param item сущность
     */
    void update(Integer id, T item);

    /**
     * Принудительно обновляет по всем прописанным поям в item (в том числе и null)
     *
     * @param id индектификатор сущности
     * @param item сущность
     */
    @SuppressWarnings("unused")
    void fullUpdate(Integer id, T item);

    Optional<T> getById(Integer id);

    List<T> getAll();

    List<T> getRange(Long limit, Long offset);

}
