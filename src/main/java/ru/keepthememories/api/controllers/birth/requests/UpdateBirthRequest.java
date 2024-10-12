package ru.keepthememories.api.controllers.birth.requests;

import java.util.Optional;

public record UpdateBirthRequest(Integer id,
                                 Optional<String> date,
                                 Optional<Integer> biologicalMotherId,
                                 Optional<Integer> biologicalFatherId) {
}
