package ru.keepthememories.api.requests;

import java.util.Optional;

public record DefaultQueryRequest(Optional<Integer> id,
                                  Optional<Long> limit,
                                  Optional<Long> offset) {
}
