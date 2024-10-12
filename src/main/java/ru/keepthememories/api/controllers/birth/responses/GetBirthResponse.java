package ru.keepthememories.api.controllers.birth.responses;

import ru.keepthememories.domain.models.Birth;

import java.util.List;

public record GetBirthResponse(List<Birth> births) {
}
