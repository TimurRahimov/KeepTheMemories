package ru.keepthememories.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.keepthememories.domain.models.Person;
import ru.keepthememories.repositories.interfaces.AbstractPersonRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonRepository implements AbstractPersonRepository {

    private final Connection connection;
    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(PersonRepository.class);

    PersonRepository() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:test.db");
        createTable();
    }

    @Override
    synchronized public Integer add(Person item) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Person (surname, name, patronymic) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getSurname());
            statement.setString(2, item.getName());
            statement.setString(3, item.getPatronymic());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Person> get(Integer personId) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Person WHERE id = ?")) {
            statement.setInt(1, personId);
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
    public List<Person> get(Long limit, Long offset) {
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
