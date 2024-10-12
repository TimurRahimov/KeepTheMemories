package ru.keepthememories.domain.mappers;

import org.springframework.stereotype.Component;
import ru.keepthememories.domain.entities.BirthEntity;
import ru.keepthememories.domain.models.Birth;
import ru.keepthememories.domain.models.Person;
import ru.keepthememories.domain.interfaces.mappers.AbstractDomainMapper;
import ru.keepthememories.services.PersonService;
import ru.keepthememories.util.CalendarFormatter;

import java.text.SimpleDateFormat;
import java.util.Optional;

@Component
public class BirthMapper implements AbstractDomainMapper<Birth, BirthEntity> {

    private final PersonService personService;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    BirthMapper(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public BirthEntity toEntity(Birth model) {
        return BirthEntity.builder()
                .personId(model.getPerson().getPersonId())
                .date(CalendarFormatter.format(model.getDate()))
                .biologicalMotherId(model.getBiologicalMother().map(Person::getPersonId))
                .biologicalFatherId(model.getBiologicalFather().map(Person::getPersonId))
                .build();
    }

    @Override
    public Birth toDto(BirthEntity birthEntity) {
        Birth.BirthBuilder builder = Birth.builder();

        Optional<Person> person = personService.getById(birthEntity.getPersonId());
        if (person.isEmpty())
            throw new RuntimeException("Не удалось найти Person с заданным id");
        builder.person(person.get());

        builder.date(CalendarFormatter.parse(birthEntity.getDate()));

        if (birthEntity.getBiologicalMotherId().isPresent()) {
            Optional<Person> biologicalMother = personService.getById(birthEntity.getBiologicalMotherId().get());
            if (biologicalMother.isPresent())
                builder.biologicalMother(biologicalMother);
        }
        if (birthEntity.getBiologicalFatherId().isPresent()) {
            Optional<Person> biologicalFather = personService.getById(birthEntity.getBiologicalFatherId().get());
            if (biologicalFather.isPresent())
                builder.biologicalFather(biologicalFather);
        }

        return builder.build();
    }

    @Override
    public Optional<BirthEntity> toEntity(Optional<Birth> model) {
        return model.map(this::toEntity);
    }

    @Override
    public Optional<Birth> toDto(Optional<BirthEntity> birthEntity) {
        return birthEntity.map(this::toDto);
    }

}
