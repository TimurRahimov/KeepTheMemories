package ru.keepthememories.domain.entities;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder(builderClassName = "Builder")
@Getter
public class BirthEntity {

    private Integer personId;
    private String date;
    @lombok.Builder.Default
    private Optional<Integer> biologicalMotherId = Optional.empty();
    @lombok.Builder.Default
    private Optional<Integer> biologicalFatherId = Optional.empty();

}
