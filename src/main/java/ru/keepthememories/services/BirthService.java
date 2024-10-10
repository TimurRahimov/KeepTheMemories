package ru.keepthememories.services;

import org.springframework.stereotype.Component;
import ru.keepthememories.dao.BirthEntityDao;
import ru.keepthememories.dao.entities.BirthEntity;
import ru.keepthememories.domain.models.Birth;
import ru.keepthememories.mappers.BirthMapper;
import ru.keepthememories.services.interfaces.Gettable;
import ru.keepthememories.services.interfaces.Updatable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BirthService implements Gettable<Birth>, Updatable<Birth> {

    private final BirthEntityDao dao;
    private final BirthMapper birthMapper;

    BirthService(BirthEntityDao dao,
                 BirthMapper birthMapper) {
        this.dao = dao;
        this.birthMapper = birthMapper;
    }

    public Birth add(Integer personId, String date) {
        BirthEntity birthEntity = BirthEntity.builder().personId(personId).date(date).build();
        dao.add(birthEntity);
        return birthMapper.toDto(birthEntity);
    }

    public Birth add(Integer personId, String date,
                     Optional<Integer> biologicalMotherId,
                     Optional<Integer> biologicalFatherId) {
        BirthEntity birthEntity = BirthEntity.builder()
                .personId(personId)
                .date(date)
                .biologicalMotherId(biologicalMotherId)
                .biologicalFatherId(biologicalFatherId)
                .build();
        dao.add(birthEntity);
        return birthMapper.toDto(birthEntity);
    }

    @Override
    public Optional<Birth> getById(Integer personId) {
        Optional<BirthEntity> birth = dao.getById(personId);
        return birth.map(birthMapper::toDto);
    }

    @Override
    public List<Birth> getRange(Long limit, Long offset) {
        return dao.getRange(limit, offset).stream().map(birthMapper::toDto).toList();
    }

    @Override
    public List<Birth> getAll() {
        return dao.getAll().stream().map(birthMapper::toDto).toList();
    }

    @Override
    public void update(Integer personId, Birth birth) {
        dao.update(personId, birthMapper.toEntity(birth));
    }

}
