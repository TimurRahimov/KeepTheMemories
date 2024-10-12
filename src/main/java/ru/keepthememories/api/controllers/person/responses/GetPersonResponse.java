package ru.keepthememories.api.controllers.person.responses;

import ru.keepthememories.domain.models.Person;

import java.util.List;

public record GetPersonResponse(List<Person> persons) {
}
