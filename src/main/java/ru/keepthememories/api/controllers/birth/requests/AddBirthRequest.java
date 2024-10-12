package ru.keepthememories.api.controllers.birth.requests;

import java.util.Optional;

public record AddBirthRequest(Integer personId,
                              String date,
                              Optional<Integer> biologicalMotherId,
                              Optional<Integer> biologicalFatherId) {
}
