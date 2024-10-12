package ru.keepthememories.domain.interfaces.services;

import java.util.List;
import java.util.Optional;

public interface Gettable<T> {

    Optional<T> getById(Integer id);

    List<T> getRange(Long limit, Long offset);

    List<T> getAll();

}
