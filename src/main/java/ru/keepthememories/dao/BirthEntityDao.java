package ru.keepthememories.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.keepthememories.domain.entities.BirthEntity;
import ru.keepthememories.domain.interfaces.dao.AbstractDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BirthEntityDao implements AbstractDao<BirthEntity> {

    private final Connection connection;
    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(BirthEntityDao.class);


    BirthEntityDao(Connection connection) {
        this.connection = connection;
        createTable();
    }

    private void createTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Birth (" +
                    "person_id INT UNIQUE," +
                    "date TEXT, " +
                    "biological_mother INT, " +
                    "biological_father INT, " +
                    "CONSTRAINT birth_person_id_fk " +
                    "FOREIGN KEY (person_id) REFERENCES Person (id), " +
                    "CONSTRAINT birth_biological_mother_fk " +
                    "FOREIGN KEY (biological_mother) REFERENCES Person (id), " +
                    "CONSTRAINT birth_biological_father_fk " +
                    "FOREIGN KEY (biological_father) REFERENCES Person (id)" +
                    ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer add(BirthEntity item) {
        String sql = "INSERT INTO Birth (person_id, date, biological_mother, biological_father) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setNull(1, Types.INTEGER);
            statement.setNull(2, Types.VARCHAR);
            statement.setNull(3, Types.INTEGER);
            statement.setNull(4, Types.INTEGER);

            statement.setInt(1, item.getPersonId());
            statement.setString(2, item.getDate());
            if (item.getBiologicalMotherId().isPresent())
                statement.setInt(3, item.getBiologicalMotherId().get());
            if (item.getBiologicalFatherId().isPresent())
                statement.setInt(4, item.getBiologicalFatherId().get());

            statement.execute();
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(List<BirthEntity> list) {
        list.forEach(this::add);
    }

    @Override
    public void delete(Integer id) {
        // TODO BirthEntityDao.delete
    }

    @Override
    public void update(Integer id, BirthEntity item) {
        String sql = "UPDATE Birth " +
                "SET " +
                "date = IFNULL(?, date), " +
                "biological_mother = IFNULL(?, biological_mother), " +
                "biological_father = IFNULL(?, biological_father) " +
                "WHERE person_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setNull(1, Types.VARCHAR);
            statement.setNull(2, Types.INTEGER);
            statement.setNull(3, Types.INTEGER);
            statement.setInt(4, id);

            statement.setString(1, item.getDate());
            if (item.getBiologicalMotherId().isPresent())
                statement.setInt(2, item.getBiologicalMotherId().get());
            if (item.getBiologicalFatherId().isPresent())
                statement.setInt(3, item.getBiologicalFatherId().get());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<BirthEntity> getById(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Birth WHERE person_id = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                BirthEntity.Builder builder = BirthEntity.builder();
                builder.personId(rs.getInt("person_id"));
                builder.date(rs.getString("date"));
                int biologicalMotherId = rs.getInt("biological_mother");
                if (!rs.wasNull() && biologicalMotherId != 0)
                    builder.biologicalMotherId(Optional.of(biologicalMotherId));
                int biologicalFatherId = rs.getInt("biological_father");
                if (!rs.wasNull() && biologicalFatherId != 0)
                    builder.biologicalFatherId(Optional.of(biologicalFatherId));
                return Optional.ofNullable(builder.build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<BirthEntity> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Birth")) {
            ResultSet rs = statement.executeQuery();
            List<BirthEntity> resultList = new ArrayList<>();
            while (rs.next()) {
                BirthEntity.Builder builder = BirthEntity.builder();
                builder.personId(rs.getInt("person_id"));
                builder.date(rs.getString("date"));
                int biologicalMotherId = rs.getInt("biological_mother");

                if (!rs.wasNull() && biologicalMotherId != 0)
                    builder.biologicalMotherId(Optional.of(biologicalMotherId));
                int biologicalFatherId = rs.getInt("biological_father");
                if (!rs.wasNull() && biologicalFatherId != 0)
                    builder.biologicalFatherId(Optional.of(biologicalFatherId));
                resultList.add(builder.build());
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BirthEntity> getRange(Long limit, Long offset) {
        // TODO BirthEntityDao.getRange
        return List.of();
    }

}
