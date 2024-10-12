package ru.keepthememories.services;

import org.springframework.stereotype.Component;
import ru.keepthememories.domain.entities.PersonEntity;
import ru.keepthememories.domain.interfaces.dao.AbstractDao;
import ru.keepthememories.domain.models.Person;
import ru.keepthememories.domain.mappers.PersonMapper;
import ru.keepthememories.domain.interfaces.services.Deletable;
import ru.keepthememories.domain.interfaces.services.Gettable;
import ru.keepthememories.domain.interfaces.services.Updatable;

import java.util.List;
import java.util.Optional;

@Component
public class PersonService implements Deletable<Person>, Gettable<Person>, Updatable<Person> {

    private final AbstractDao<PersonEntity> dao;
    private final PersonMapper personMapper;

    PersonService(AbstractDao<PersonEntity> dao,
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

    @Override
    public void delete(Integer personId) {
        dao.delete(personId);
    }

    @Override
    public List<Person> getRange(Long limit, Long offset) {
        return dao.getRange(limit, offset).stream().map(personMapper::toDto).toList();
    }

    @Override
    public Optional<Person> getById(Integer personId) {
        if (personId == null)
            return Optional.empty();
        Optional<PersonEntity> person = dao.getById(personId);
        return person.map(personMapper::toDto);
    }

    @Override
    public List<Person> getAll() {
        return dao.getAll().stream().map(personMapper::toDto).toList();
    }

    @Override
    public void update(Integer personId, Person person) {
        dao.update(personId, personMapper.toEntity(person));
    }

}
