package ru.keepthememories.api.controllers.person.requests;

import ru.keepthememories.domain.models.Person;

public record AddPersonRequest(String surname, String name, String patronymic, Person.Sex sex) {
}
