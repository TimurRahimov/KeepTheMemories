package ru.keepthememories.domain.entities;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder(builderClassName = "Builder")
@Getter
public class PersonEntity {

    private Integer personId;
    private String sex;
    @lombok.Builder.Default
    private Optional<String> surname = Optional.empty();
    @lombok.Builder.Default
    private Optional<String> name = Optional.empty();
    @lombok.Builder.Default
    private Optional<String> patronymic = Optional.empty();

    @Override
    public String toString() {
        return "PersonEntity{" +
                "id=" + personId +
                ", sex='" + sex + '\'' +
                ", surname=" + surname +
                ", name=" + name +
                ", patronymic=" + patronymic +
                '}';
    }

}
