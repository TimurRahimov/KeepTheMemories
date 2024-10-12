package ru.keepthememories.domain.models;

import lombok.Builder;
import lombok.Getter;

import java.util.Calendar;

@Builder
@Getter
public class Death {

    private Person person;
    private Calendar date;
    @Builder.Default
    private String reason = "";

}
