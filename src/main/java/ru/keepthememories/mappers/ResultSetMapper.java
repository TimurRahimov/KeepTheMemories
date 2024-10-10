package ru.keepthememories.mappers;

import org.springframework.stereotype.Component;
import ru.keepthememories.dao.entities.PersonEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class ResultSetMapper {

    public PersonEntity toPersonEntity(ResultSet resultSet) throws SQLException {
        return PersonEntity.builder()
                .personId(resultSet.getInt("id"))
                .surname(Optional.ofNullable(resultSet.getString("surname")))
                .name(Optional.ofNullable(resultSet.getString("name")))
                .patronymic(Optional.ofNullable(resultSet.getString("patronymic")))
                .sex(resultSet.getString("sex"))
                .build();
    }

}
