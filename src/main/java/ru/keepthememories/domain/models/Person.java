package ru.keepthememories.domain.models;

import lombok.Getter;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Getter
public class Person {

    private Integer personId;
    private Optional<String> surname = Optional.empty();
    private Optional<String> name = Optional.empty();
    private Optional<String> patronymic = Optional.empty();

    private Person() {
    }

    public static Builder getBuilder() {
        return new Person().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        @SuppressWarnings("unused")
        public Builder copyPerson(Person person) {
            setPersonId(person.personId);
            person.surname.ifPresent(this::setSurname);
            person.name.ifPresent(this::setName);
            person.patronymic.ifPresent(this::setPatronymic);
            return this;
        }

        public Builder setPersonId(Integer personId) {
            Person.this.personId = personId;
            return this;
        }

        public Builder setSurname(String surname) {
            Person.this.surname = Optional.ofNullable(surname);
            return this;
        }

        public Builder setName(String name) {
            Person.this.name = Optional.ofNullable(name);
            return this;
        }

        public Builder setPatronymic(String patronymic) {
            Person.this.patronymic = Optional.ofNullable(patronymic);
            return this;
        }

        public Person build() {
            return Person.this;
        }

    }

}
