package ru.keepthememories.api.controllers.person.requests;

import ru.keepthememories.domain.models.Person;

import java.util.Optional;

public record UpdatePersonRequest(Integer id,
                                  Optional<String> surname,
                                  Optional<String> name,
                                  Optional<String> patronymic,
                                  Optional<Person.Sex> sex) {
}
