package ru.keepthememories.dao.entities;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
@Getter
public class BirthEntity {

    private Integer personId;
    private String date;
    @Builder.Default
    private Optional<Integer> biologicalMotherId = Optional.empty();
    @Builder.Default
    private Optional<Integer> biologicalFatherId = Optional.empty();

}
