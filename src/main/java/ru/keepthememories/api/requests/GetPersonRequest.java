package ru.keepthememories.api.requests;

import java.util.Optional;

public record GetPersonRequest(Optional<Long> limit, Optional<Long> offset, Optional<Integer> personId) {
}
