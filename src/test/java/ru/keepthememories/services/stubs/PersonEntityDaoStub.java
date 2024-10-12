package ru.keepthememories.services.stubs;

import org.springframework.boot.test.context.TestComponent;
import ru.keepthememories.domain.entities.PersonEntity;
import ru.keepthememories.domain.interfaces.dao.AbstractDao;

import java.util.*;

@TestComponent
public class PersonEntityDaoStub implements AbstractDao<PersonEntity> {

    Map<Integer, PersonEntity> entityMap = new LinkedHashMap<>();
    Integer available_id = 1;

    @Override
    public Integer add(PersonEntity item) {
        entityMap.put(available_id, PersonEntity.builder()
                .personId(available_id)
                .surname(item.getSurname())
                .name(item.getName())
                .patronymic(item.getPatronymic())
                .sex(item.getSex())
                .build());
        return available_id++;
    }

    @Override
    public void add(List<PersonEntity> list) {
        list.forEach(this::add);
    }

    @Override
    public void delete(Integer id) {
        if (entityMap.get(id) == null)
            return;
        entityMap.remove(id);
    }

    @Override
    public void update(Integer id, PersonEntity item) {
        if (entityMap.get(id) == null)
            return;
        PersonEntity personEntity = entityMap.get(id);

//        if (item.getSurname().isPresent())
//            statement.setString(1, item.getSurname().get());
//        if (item.getName().isPresent())
//            statement.setString(2, item.getName().get());
//        if (item.getPatronymic().isPresent())
//            statement.setString(3, item.getPatronymic().get());
//        statement.setString(4, item.getSex());
    }

    @Override
    public Optional<PersonEntity> getById(Integer id) {
        return entityMap.get(id) == null ? Optional.empty() : Optional.ofNullable(entityMap.get(id));
    }

    @Override
    public List<PersonEntity> getAll() {
        return entityMap.values().stream().toList();
    }

    @Override
    public List<PersonEntity> getRange(Long limit, Long offset) {
        return entityMap.values().stream().skip(limit).limit(offset).toList();
    }

}
