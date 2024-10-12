package ru.keepthememories.domain.mappers;

import org.springframework.stereotype.Component;
import ru.keepthememories.domain.entities.PersonEntity;
import ru.keepthememories.domain.models.Person;
import ru.keepthememories.domain.interfaces.mappers.AbstractDomainMapper;

import java.util.Optional;

@Component
public class PersonMapper implements AbstractDomainMapper<Person, PersonEntity> {

    @Override
    public PersonEntity toEntity(Person model) {
        return PersonEntity.builder()
                .personId(model.getPersonId())
                .surname(model.getSurname())
                .name(model.getName())
                .patronymic(model.getPatronymic())
                .sex(model.getSex() == null ? null : model.getSex().toString())
                .build();
    }

    @Override
    public Person toDto(PersonEntity personEntity) {
        return Person.builder()
                .personId(personEntity.getPersonId())
                .surname(personEntity.getSurname())
                .name(personEntity.getName())
                .patronymic(personEntity.getPatronymic())
                .sex(personEntity.getSex() == null ? null : Person.Sex.valueOf(personEntity.getSex()))
                .build();
    }

    @Override
    public Optional<PersonEntity> toEntity(Optional<Person> model) {
        return model.map(this::toEntity);
    }

    @Override
    public Optional<Person> toDto(Optional<PersonEntity> personEntity) {
        return personEntity.map(this::toDto);
    }

}
