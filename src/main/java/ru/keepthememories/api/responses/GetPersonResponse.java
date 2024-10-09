package ru.keepthememories.api.responses;

import ru.keepthememories.domain.models.Person;

import java.util.List;

public record GetPersonResponse(List<Person> list) {
}
