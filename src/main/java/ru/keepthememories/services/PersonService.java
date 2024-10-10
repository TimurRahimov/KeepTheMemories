package ru.keepthememories.services;

import org.springframework.stereotype.Component;
import ru.keepthememories.dao.PersonEntityDao;
import ru.keepthememories.dao.entities.PersonEntity;
import ru.keepthememories.domain.models.Person;
import ru.keepthememories.mappers.PersonMapper;
import ru.keepthememories.services.interfaces.Gettable;

import java.util.List;
import java.util.Optional;

@Component
public class PersonService implements Gettable<Person> {

    private final PersonEntityDao dao;
    private final PersonMapper personMapper;

    PersonService(PersonEntityDao dao,
                  PersonMapper personMapper) {
        this.dao = dao;
        this.personMapper = personMapper;
    }

    public Person add(String surname, String name, String patronymic, Person.Sex sex) {
        Person.PersonBuilder personBuilder = Person.builder()
                .sex(sex)
                .surname(Optional.ofNullable(surname))
                .name(Optional.ofNullable(name))
                .patronymic(Optional.ofNullable(patronymic));

        Integer personId = dao.add(personMapper.toEntity(personBuilder.build()));
        return personBuilder.personId(personId).build();
    }

    public List<Person> getRange(Long limit, Long offset) {
        return dao.getRange(limit, offset).stream().map(personMapper::toDto).toList();
    }

    public Optional<Person> getById(Integer personId) {
        Optional<PersonEntity> person = dao.getById(personId);
        return person.map(personMapper::toDto);
    }

    public List<Person> getAll() {
        return dao.getAll().stream().map(personMapper::toDto).toList();
    }

    public void delete(Integer personId) {
        dao.delete(personId);
    }

    public void update(Integer personId, Person person) {
        dao.update(personId, personMapper.toEntity(person));
    }

}
