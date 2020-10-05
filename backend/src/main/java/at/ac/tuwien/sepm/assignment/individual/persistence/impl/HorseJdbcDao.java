package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.sql.*;

@Repository
public class HorseJdbcDao implements HorseDao {

    private static final String TABLE_NAME = "Horse";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public HorseJdbcDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Horse save(Horse horse) throws PersistenceException {
        LOGGER.trace("Save horse with name: " + horse.getName());
        final String sql = "INSERT INTO " + TABLE_NAME + " (ID, NAME, DESCRIPTION, BIRTH_DATE, IS_MALE, BREED_ID)" +
            " VALUES (null, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, horse.getName());
                stmt.setString(2, horse.getDescription());
                stmt.setDate(3, horse.getBirthDate());
                stmt.setInt(4, horse.isMale() ? 1 : 0);
                if (horse.getBreedId() != null) {
                    stmt.setLong(5, horse.getBreedId());
                } else {
                    stmt.setNull(5, Types.BIGINT);
                }
                LOGGER.debug("Query: " + stmt.toString());
                return stmt;
            }, keyHolder);
        } catch (DataAccessException e){
            throw new PersistenceException("Failed to save horse.");
        }

        horse.setId(((Number)keyHolder.getKeys().get("id")).longValue());

        return horse;
    }

    private Breed mapRow(ResultSet resultSet, int i) throws SQLException {
        final Breed breed = new Breed();
        breed.setId(resultSet.getLong("id"));
        breed.setName(resultSet.getString("name"));
        return breed;
    }
}
