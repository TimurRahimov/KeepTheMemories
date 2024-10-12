package ru.keepthememories.mappers;

import org.springframework.stereotype.Component;
import ru.keepthememories.dao.entities.BirthEntity;
import ru.keepthememories.domain.models.Birth;
import ru.keepthememories.domain.models.Person;
import ru.keepthememories.mappers.interfaces.AbstractDomainMapper;
import ru.keepthememories.services.PersonService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
                .date(format.format(model.getDate()))
                .biologicalMotherId(model.getBiologicalMother().map(Person::getPersonId))
                .biologicalFatherId(model.getBiologicalFather().map(Person::getPersonId))
                .build();
    }

    @Override
    public Birth toDto(BirthEntity birthEntity) {
        Birth.Builder builder = Birth.getBuilder();

        Optional<Person> person = personService.getById(birthEntity.getPersonId());
        if (person.isEmpty())
            throw new RuntimeException("Не удалось найти Person с заданным id");
        builder.setPerson(person.get());

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(birthEntity.getDate()));
            builder.setDate(calendar);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (birthEntity.getBiologicalMotherId().isPresent()) {
            Optional<Person> biologicalMother = personService.getById(birthEntity.getBiologicalMotherId().get());
            if (biologicalMother.isPresent())
                builder.setBiologicalMother(biologicalMother);
        }
        if (birthEntity.getBiologicalFatherId().isPresent()) {
            Optional<Person> biologicalFather = personService.getById(birthEntity.getBiologicalFatherId().get());
            if (biologicalFather.isPresent())
                builder.setBiologicalFather(biologicalFather);
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
