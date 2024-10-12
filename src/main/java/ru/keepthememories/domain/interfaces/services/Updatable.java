package ru.keepthememories.domain.interfaces.services;

public interface Updatable<T> {

    void update(Integer id, T item);

}
