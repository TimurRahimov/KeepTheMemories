package ru.keepthememories.services;

import org.springframework.stereotype.Component;
import ru.keepthememories.dao.interfaces.AbstractDao;
import ru.keepthememories.domain.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonService {

    private final AbstractDao<Person> repository;

    PersonService(AbstractDao<Person> personRepository) {
        this.repository = personRepository;
    }

    public Person add(String surname, String name, String patronymic) {
        Person.Builder personBuilder = Person.getBuilder()
                .setSurname(surname)
                .setName(name)
                .setPatronymic(patronymic);

        Integer personId = repository.add(personBuilder.build());
        return personBuilder.setPersonId(personId).build();
    }

    public List<Person> get(Long limit, Long offset) {
        return repository.getRange(limit, offset);
    }

    public List<Person> get(Integer personId) {
        Optional<Person> person = repository.getById(personId);
        return person.map(List::of).orElseGet(ArrayList::new);
    }

    public List<Person> getAll() {
        return repository.getAll();
    }

    public void update(Integer personId, Person person) {
        repository.update(personId, person);
    }

}
