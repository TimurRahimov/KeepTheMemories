package ru.keepthememories.domain.models;

import lombok.Getter;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

@Getter
public class Birth {

    private Person person;
    private Calendar date;
    private Optional<Person> biologicalMother;
    private Optional<Person> biologicalFather;

    private Birth() {
    }

    public static Builder getBuilder() {
        return new Birth().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setPerson(Person person) {
            Birth.this.person = person;
            return this;
        }

        public Builder setDate(Calendar birthDate) {
            Birth.this.date = birthDate;
            return this;
        }

        public Builder setDate(int day, int month, int year) {
            month--;
            Birth.this.date = new GregorianCalendar(year, --month, day);
            return this;
        }

        public Builder setBiologicalMother(Optional<Person> mother) {
            Birth.this.biologicalMother = mother;
            return this;
        }

        public Builder setBiologicalFather(Optional<Person> father) {
            Birth.this.biologicalFather = father;
            return this;
        }

        public Birth build() {
            return Birth.this;
        }

    }

}
