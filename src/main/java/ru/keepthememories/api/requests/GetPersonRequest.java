package ru.keepthememories.api.requests;

import java.util.Optional;

public record GetPersonRequest(Optional<Long> count, Optional<Integer> personId) {
}
