package ru.keepthememories.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.keepthememories.dao.entities.PersonEntity;
import ru.keepthememories.dao.interfaces.AbstractDao;
import ru.keepthememories.mappers.ResultSetMapper;

import java.sql.*;
import java.util.*;

@Component
public class PersonEntityDao implements AbstractDao<PersonEntity> {

    private final Connection connection;
    private final ResultSetMapper resultSetMapper;
    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(PersonEntityDao.class);

    PersonEntityDao(Connection connection,
                    ResultSetMapper resultSetMapper) {
        this.connection = connection;
        this.resultSetMapper = resultSetMapper;
        createTable();
    }

    private void createTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Person (" +
                    "id INTEGER PRIMARY KEY," +
                    "surname TEXT, " +
                    "name TEXT, " +
                    "patronymic TEXT, " +
                    "sex TEXT " +
                    ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addSexField() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("ALTER TABLE Person ADD COLUMN sex TEXT");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    synchronized public Integer add(PersonEntity item) {
        String sql = "INSERT INTO Person (surname, name, patronymic, sex) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setNull(1, Types.VARCHAR);
            statement.setNull(2, Types.VARCHAR);
            statement.setNull(3, Types.VARCHAR);
            statement.setNull(4, Types.VARCHAR);

            if (item.getSurname().isPresent())
                statement.setString(1, item.getSurname().get());
            if (item.getName().isPresent())
                statement.setString(2, item.getName().get());
            if (item.getPatronymic().isPresent())
                statement.setString(3, item.getPatronymic().get());
            statement.setString(4, item.getSex());

            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(List<PersonEntity> list) {
        list.forEach(this::add);
    }

    @Override
    public void delete(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM Person WHERE id = ?")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Integer id, PersonEntity item) {
        String sql = "UPDATE Person " +
                "SET " +
                "surname = IFNULL(?, surname), " +
                "name = IFNULL(?, name), " +
                "patronymic = IFNULL(?, patronymic), " +
                "sex = IFNULL(?, sex) " +
                "WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setNull(1, Types.VARCHAR);
            statement.setNull(2, Types.VARCHAR);
            statement.setNull(3, Types.VARCHAR);
            statement.setNull(4, Types.VARCHAR);
            statement.setInt(5, id);

            if (item.getSurname().isPresent())
                statement.setString(1, item.getSurname().get());
            if (item.getName().isPresent())
                statement.setString(2, item.getName().get());
            if (item.getPatronymic().isPresent())
                statement.setString(3, item.getPatronymic().get());
            statement.setString(4, item.getSex());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<PersonEntity> getById(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Person WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(resultSetMapper.toPersonEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<PersonEntity> getRange(Long limit, Long offset) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Person " +
                        "ORDER BY id " +
                        "LIMIT ? OFFSET ?")) {
            statement.setLong(1, limit);
            statement.setLong(2, offset);
            ResultSet resultSet = statement.executeQuery();
            List<PersonEntity> resultList = new ArrayList<>();
            while (resultSet.next()) {
                resultList.add(resultSetMapper.toPersonEntity(resultSet));
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PersonEntity> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Person")) {
            ResultSet resultSet = statement.executeQuery();
            List<PersonEntity> resultList = new ArrayList<>();
            while (resultSet.next()) {
                resultList.add(resultSetMapper.toPersonEntity(resultSet));
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
