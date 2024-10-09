package ru.keepthememories.repositories;

import ru.keepthememories.domain.models.Person;
import ru.keepthememories.repositories.interfaces.AbstractPersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public class PersonRepositoryStub implements AbstractPersonRepository {

    private final List<Person> list = new ArrayList<>();
    private Integer indexCount = 1;

    synchronized public Integer add(Person item) {
        Person.Builder builder = Person.getBuilder();
        builder.copyPerson(item)
                .setPersonId(indexCount);
        list.add(builder.build());
        return indexCount++;
    }

    public Optional<Person> get(Integer personId) {
        return list.stream().filter(person -> person.getPersonId().equals(personId)).findFirst();
    }

    public List<Person> get(Long count) {
        return list.stream().limit(count).toList();
    }

    public List<Person> getAll() {
        return list;
    }

}
