package ru.keepthememories.api.mappers;

import org.springframework.stereotype.Component;
import ru.keepthememories.api.controllers.birth.requests.UpdateBirthRequest;
import ru.keepthememories.domain.interfaces.mappers.AbstractApiMapper;
import ru.keepthememories.domain.models.Birth;
import ru.keepthememories.services.PersonService;
import ru.keepthememories.util.CalendarFormatter;

import java.util.Optional;

@Component
public class UpdateBirthRequestMapper implements AbstractApiMapper<UpdateBirthRequest, Birth> {

    private final PersonService personService;

    UpdateBirthRequestMapper(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public Birth toDto(UpdateBirthRequest request) {
        return Birth.builder()
                .person(personService.getById(request.id()).orElse(null))
                .date(CalendarFormatter.parse(request.date().orElse(null)))
                .biologicalMother(
                        request.biologicalMotherId().isEmpty() ? Optional.empty() :
                        personService.getById(request.biologicalMotherId().get())
                )
                .biologicalFather(
                        request.biologicalFatherId().isEmpty() ? Optional.empty() :
                        personService.getById(request.biologicalFatherId().get())
                )
                .build();
    }

}
