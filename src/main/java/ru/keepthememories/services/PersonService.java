package ru.keepthememories.services;

import org.springframework.stereotype.Component;
import ru.keepthememories.domain.models.Person;
import ru.keepthememories.repositories.interfaces.AbstractPersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonService {

    private final AbstractPersonRepository repository;

    PersonService(AbstractPersonRepository personRepository) {
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

    public List<Person> get(Long count) {
        return repository.get(count);
    }

    public List<Person> get(Integer personId) {
        Optional<Person> person = repository.get(personId);
        return person.map(List::of).orElseGet(ArrayList::new);
    }

    public List<Person> getAll() {
        return repository.getAll();
    }

}
