package ru.keepthememories.services.interfaces;

public interface Updatable<T> {

    void update(Integer id, T item);

}
