package ru.keepthememories.api.controllers.person.requests;

import java.util.Optional;

public record GetPersonRequest(Optional<Long> limit,
                               Optional<Long> offset,
                               Optional<Integer> id) {
}
