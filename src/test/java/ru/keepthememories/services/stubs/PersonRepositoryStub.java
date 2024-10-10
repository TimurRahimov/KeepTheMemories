//package ru.keepthememories.repositories;
//
//import ru.keepthememories.domain.models.Person;
//import ru.keepthememories.repositories.interfaces.AbstractPersonRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@SuppressWarnings("unused")
//public class PersonRepositoryStub implements AbstractPersonRepository {
//
//    private final List<Person> list = new ArrayList<>();
//    private Integer indexCount = 1;
//
//    @Override
//    synchronized public Integer add(Person item) {
//        Person.Builder builder = Person.getBuilder();
//        builder.copyPerson(item)
//                .setPersonId(indexCount);
//        list.add(builder.build());
//        return indexCount++;
//    }
//
//    @Override
//    public Optional<Person> getById(Integer id) {
//        return list.stream().filter(person -> person.getPersonId().equals(id)).findFirst();
//    }
//
//    @Override
//    public List<Person> getRange(Long limit, Long offset) {
//        return List.of();
//    }
//
//    @Override
//    public List<Person> getAll() {
//        return list;
//    }
//
//}
