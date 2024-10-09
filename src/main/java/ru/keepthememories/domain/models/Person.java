package ru.keepthememories.domain.models;

public class Person {

    private Integer personId;
    private String surname;
    private String name;
    private String patronymic;

    private Person() {
    }

    public Integer getPersonId() {
        return personId;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public static Builder getBuilder() {
        return new Person().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder copyPerson(Person person) {
            setPersonId(person.personId);
            setSurname(person.surname);
            setName(person.name);
            setPatronymic(person.patronymic);
            return this;
        }

        public Builder setPersonId(Integer personId) {
            Person.this.personId = personId;
            return this;
        }

        public Builder setSurname(String surname) {
            Person.this.surname = surname;
            return this;
        }

        public Builder setName(String name) {
            Person.this.name = name;
            return this;
        }

        public Builder setPatronymic(String patronymic) {
            Person.this.patronymic = patronymic;
            return this;
        }

        public Person build() {
            return Person.this;
        }

    }

}
