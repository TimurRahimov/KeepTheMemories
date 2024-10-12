package ru.keepthememories.domain.models;

import lombok.Builder;
import lombok.Getter;

import java.util.Calendar;
import java.util.Optional;

@Builder
@Getter
public class Birth {

    private Person person;
    private Calendar date;
    @Builder.Default
    private Optional<Person> biologicalMother = Optional.empty();
    @Builder.Default
    private Optional<Person> biologicalFather = Optional.empty();

}
