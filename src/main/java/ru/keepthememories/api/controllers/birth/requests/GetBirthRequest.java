package ru.keepthememories.api.controllers.birth.requests;

import java.util.Optional;

public record GetBirthRequest(Optional<Long> limit, Optional<Long> offset, Optional<Integer> id) {
}
