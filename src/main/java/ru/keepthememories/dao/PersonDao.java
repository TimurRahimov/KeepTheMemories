package ru.keepthememories.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.keepthememories.domain.models.Person;
import ru.keepthememories.dao.interfaces.AbstractDao;

import java.sql.*;
import java.util.*;

@Component
public class PersonDao implements AbstractDao<Person> {

    private final Connection connection;
    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(PersonDao.class);

    PersonDao() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:test.db");
        createTable();
    }

    @Override
    synchronized public Integer add(Person item) {
        String sql = "INSERT INTO Person (surname, name, patronymic) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setNull(1, Types.VARCHAR);
            statement.setNull(2, Types.VARCHAR);
            statement.setNull(3, Types.VARCHAR);

            if (item.getSurname().isPresent())
                statement.setString(1, item.getSurname().get());
            if (item.getName().isPresent())
                statement.setString(2, item.getName().get());
            if (item.getPatronymic().isPresent())
                statement.setString(3, item.getPatronymic().get());

            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(List<Person> list) {
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
    public void update(Integer id, Person item) {
        String sql = "UPDATE Person " +
                "SET " +
                "surname = IFNULL(?, surname), " +
                "name = IFNULL(?, name), " +
                "patronymic = IFNULL(?, patronymic) " +
                "WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setNull(1, Types.VARCHAR);
            statement.setNull(2, Types.VARCHAR);
            statement.setNull(3, Types.VARCHAR);
            statement.setInt(4, id);

            if (item.getSurname().isPresent())
                statement.setString(1, item.getSurname().get());
            if (item.getName().isPresent())
                statement.setString(2, item.getName().get());
            if (item.getPatronymic().isPresent())
                statement.setString(3, item.getPatronymic().get());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fullUpdate(Integer id, Person item) {
        String sql = "UPDATE Person " +
                "SET " +
                "surname = ?, " +
                "name = ?, " +
                "patronymic = ? " +
                "WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setNull(1, Types.VARCHAR);
            statement.setNull(2, Types.VARCHAR);
            statement.setNull(3, Types.VARCHAR);
            statement.setInt(4, id);

            if (item.getSurname().isPresent())
                statement.setString(1, item.getSurname().get());
            if (item.getName().isPresent())
                statement.setString(2, item.getName().get());
            if (item.getPatronymic().isPresent())
                statement.setString(3, item.getPatronymic().get());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Person> getById(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Person WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Person.Builder personBuilder = Person.getBuilder();
            if (resultSet.next()) {
                personBuilder.setPersonId(resultSet.getInt("id"))
                        .setSurname(resultSet.getString("surname"))
                        .setName(resultSet.getString("name"))
                        .setPatronymic(resultSet.getString("patronymic"));
                return Optional.ofNullable(personBuilder.build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Person> getRange(Long limit, Long offset) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Person " +
                        "ORDER BY id " +
                        "LIMIT ? OFFSET ?")) {
            statement.setLong(1, limit);
            statement.setLong(2, offset);
            ResultSet resultSet = statement.executeQuery();
            List<Person> resultList = new ArrayList<>();
            while (resultSet.next()) {
                Person.Builder personBuilder = Person.getBuilder();
                personBuilder.setPersonId(resultSet.getInt("id"))
                        .setSurname(resultSet.getString("surname"))
                        .setName(resultSet.getString("name"))
                        .setPatronymic(resultSet.getString("patronymic"));
                resultList.add(personBuilder.build());
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Person> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Person")) {
            ResultSet resultSet = statement.executeQuery();
            List<Person> resultList = new ArrayList<>();
            while (resultSet.next()) {
                Person.Builder personBuilder = Person.getBuilder();
                personBuilder.setPersonId(resultSet.getInt("id"))
                        .setSurname(resultSet.getString("surname"))
                        .setName(resultSet.getString("name"))
                        .setPatronymic(resultSet.getString("patronymic"));
                resultList.add(personBuilder.build());
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Person (" +
                    "id INTEGER PRIMARY KEY," +
                    "surname TEXT, " +
                    "name TEXT, " +
                    "patronymic TEXT" +
                    ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
