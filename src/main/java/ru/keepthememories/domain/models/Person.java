package ru.keepthememories.domain.models;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Getter
@Builder
public class Person {

    public enum Sex {
        MALE, FEMALE
    }

    private Integer personId;
    private Sex sex;
    @Builder.Default
    private Optional<String> surname = Optional.empty();
    @Builder.Default
    private Optional<String> name = Optional.empty();
    @Builder.Default
    private Optional<String> patronymic = Optional.empty();

}
