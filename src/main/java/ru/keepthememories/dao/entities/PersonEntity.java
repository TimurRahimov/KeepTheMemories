package ru.keepthememories.dao.entities;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
@Getter
public class PersonEntity {

    private Integer personId;
    private String sex;
    @Builder.Default
    private Optional<String> surname = Optional.empty();
    @Builder.Default
    private Optional<String> name = Optional.empty();
    @Builder.Default
    private Optional<String> patronymic = Optional.empty();

    @Override
    public String toString() {
        return "PersonEntity{" +
                "personId=" + personId +
                ", sex='" + sex + '\'' +
                ", surname=" + surname +
                ", name=" + name +
                ", patronymic=" + patronymic +
                '}';
    }
}
