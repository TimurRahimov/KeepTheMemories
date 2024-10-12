package ru.keepthememories.domain.models;

import lombok.Getter;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Getter
public class Death {

    private Person person;
    private Calendar date;
    private String reason;

    private Death() {
    }

    public static Builder getBuilder() {
        return new Death().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setPerson(Person person) {
            Death.this.person = person;
            return this;
        }

        public Builder setDate(Calendar deathDate) {
            Death.this.date = deathDate;
            return this;
        }

        public Builder setDate(int day, int month, int year) {
            month--;
            Death.this.date = new GregorianCalendar(year, month, day);
            return this;
        }

        public Builder setReason(String reason) {
            Death.this.reason = reason;
            return this;
        }

        public Death build() {
            return Death.this;
        }

    }

}
