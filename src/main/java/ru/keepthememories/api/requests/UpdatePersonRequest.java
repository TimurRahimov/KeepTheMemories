package ru.keepthememories.api.requests;

import java.util.Optional;

public record UpdatePersonRequest(Integer id,
                                  Optional<String> surname,
                                  Optional<String> name,
                                  Optional<String> patronymic) {
}
