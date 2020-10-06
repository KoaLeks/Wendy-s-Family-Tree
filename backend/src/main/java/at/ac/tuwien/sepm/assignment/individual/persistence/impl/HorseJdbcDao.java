package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.List;

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
                stmt.setBoolean(4, horse.getIsMale());
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

    @Override
    public Horse update(Long id, Horse horse) throws NotFoundException, PersistenceException {
        LOGGER.trace("Get horse with id {} and update values", id);
        String sql = "UPDATE " + TABLE_NAME + " SET NAME=COALESCE(?, NAME), DESCRIPTION=COALESCE(?, DESCRIPTION), " +
            "BIRTH_DATE=COALESCE(?, BIRTH_DATE), IS_MALE=COALESCE(?, IS_MALE), BREED_ID=ISNULL(?, BREED_ID) WHERE ID=?";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, horse.getName());
                stmt.setString(2, horse.getDescription());
                stmt.setDate(3, horse.getBirthDate());
                stmt.setBoolean(4, horse.getIsMale());
                if (horse.getBreedId() != null) {
                    stmt.setLong(5, horse.getBreedId());
                } else {
                    stmt.setNull(5, Type.LONG);
                }
                stmt.setLong(6, id);
                LOGGER.debug("Query: " + stmt.toString());
                return stmt;
            }, keyHolder);
        } catch (DataAccessException e){
            LOGGER.error("Error during update for horse with id: " + id);
            throw new PersistenceException("Failed to update horse with id: " + id);
        }
        if (keyHolder.getKeyList().isEmpty()){
            throw new NotFoundException("Could not find horse with id: " + id);
        }
        return horse;
    }

    @Override
    public List<Horse> getAll() throws PersistenceException {
        LOGGER.trace("Get all horses");
        String sql = "SELECT * FROM " + TABLE_NAME;
        List<Horse> horseList;
        try {
            horseList = jdbcTemplate.query(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                LOGGER.debug("Query: " + stmt.toString());
                return stmt;
            }, this::mapRow);
            return horseList;
        } catch (DataAccessException e){
            LOGGER.error("Problem while getting all horses!");
            throw new PersistenceException("Could not get all horses.");
        }
    }

    private Horse mapRow(ResultSet resultSet, int i) throws SQLException {
        final Horse horse = new Horse();
        horse.setId(resultSet.getLong("id"));
        horse.setName(resultSet.getString("name"));
        horse.setDescription(resultSet.getString("description"));
        horse.setBirthDate(Date.valueOf(resultSet.getTimestamp("birth_date").toLocalDateTime().toLocalDate()));
        horse.setIsMale(resultSet.getBoolean("is_male"));
        horse.setBreed(resultSet.getLong("breed_id"));
        return horse;
    }
}
