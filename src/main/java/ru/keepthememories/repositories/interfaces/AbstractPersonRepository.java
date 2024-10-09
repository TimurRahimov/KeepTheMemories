package ru.keepthememories.repositories.interfaces;

import ru.keepthememories.domain.models.Person;

import java.util.List;
import java.util.Optional;

public interface AbstractPersonRepository {

    Integer add(Person item);

    Optional<Person> get(Integer personId);

    List<Person> get(Long count);

    List<Person> getAll();

}
