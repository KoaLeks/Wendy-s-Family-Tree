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
        final String sql = "INSERT INTO " + TABLE_NAME + " (ID, NAME, DESCRIPTION, BIRTH_DATE, IS_MALE, BREED_ID, BREED_NAME, FATHER_ID, MOTHER_ID)" +
            " VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, horse.getName());
                stmt.setString(2, horse.getDescription());
                stmt.setDate(3, horse.getBirthDate());
                stmt.setBoolean(4, horse.getIsMale());
//                LOGGER.info(""+horse);
//                LOGGER.info(""+horse.getBreed());
                if (horse.getBreed() != null && horse.getBreed().getId() != null) {
                    stmt.setLong(5, horse.getBreed().getId());
                    stmt.setString(6, horse.getBreed().getName());
                } else {
                    stmt.setNull(5, Types.BIGINT);
                    stmt.setString(6, null);
                }
                if (horse.getFather() != null && horse.getFather().getId() != null) {
                    stmt.setLong(7, horse.getFather().getId());
                } else {
                    stmt.setNull(7, Type.LONG);
                }
                if (horse.getMother() != null && horse.getMother().getId() != null) {
                    stmt.setLong(8, horse.getMother().getId());
                } else {
                    stmt.setNull(8, Type.LONG);
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
            "BIRTH_DATE=COALESCE(?, BIRTH_DATE), IS_MALE=COALESCE(?, IS_MALE), BREED_ID=?, BREED_NAME=COALESCE(?, BREED_NAME), FATHER_ID=?, MOTHER_ID=? WHERE ID=?";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, horse.getName());
                stmt.setString(2, horse.getDescription());
                stmt.setDate(3, horse.getBirthDate());
                stmt.setBoolean(4, horse.getIsMale());
//                LOGGER.info(""+horse);
//                LOGGER.info(""+horse.getBreed());
                if (horse.getBreed() != null && horse.getBreed().getId() != null ) {
                    stmt.setLong(5, horse.getBreed().getId());
                    stmt.setString(6, horse.getBreed().getName());
                } else {
                    stmt.setNull(5, Type.LONG);
                    stmt.setString(6, null);
                }
                if (horse.getFather() != null && horse.getFather().getId() != null) {
                    stmt.setLong(7, horse.getFather().getId());
                } else {
                    stmt.setNull(7, Type.LONG);
                }
                if (horse.getMother() != null && horse.getMother().getId() != null) {
                    stmt.setLong(8, horse.getMother().getId());
                } else {
                    stmt.setNull(8, Type.LONG);
                }
                stmt.setLong(9, id);
                LOGGER.debug("Query: " + stmt.toString());
                return stmt;
            }, keyHolder);
        } catch (DataAccessException e){
            throw new PersistenceException("Failed to update horse with id: " + id);
        }
        if (keyHolder.getKeyList().isEmpty()){
            throw new NotFoundException("Could not find horse with id: " + id);
        }

        return horse;
    }

    @Override
    public void delete(Long id) throws PersistenceException{
        LOGGER.trace("Delete horse with id {}", id);
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE ID=?";
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setLong(1, id);
                LOGGER.debug("Query: " + stmt.toString());
                return stmt;
            });
        } catch (DataAccessException e){
            throw new PersistenceException("Could not find horse with id: " + id);
        }
    }

    @Override
    public Horse findOneById(Long id) {
        LOGGER.trace("Get horse with id {}", id);
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID=?";
        List<Horse> horses = jdbcTemplate.query(sql, new Object[] { id }, this::mapRow);
        if (horses.isEmpty()) {
            throw new NotFoundException("Could not find horse with id " + id);
        }
        return horses.get(0);
    }

    @Override
    public List<Horse> findHorses(Horse horse) throws PersistenceException {
        LOGGER.info("Search for horses with parameter: name: {}, description: {}, birthDate: {}, isMale: {}, breed: {}",
            horse.getName(), horse.getDescription(),  horse.getBirthDate(), horse.getIsMale(), horse.getBreed());
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE UPPER(NAME) LIKE UPPER(?) AND UPPER(IFNULL(DESCRIPTION, '')) LIKE UPPER(?) " +
            "AND BIRTH_DATE <= ? AND IS_MALE LIKE ? AND IFNULL(BREED_ID, '0') LIKE ?";

        List<Horse> horseList;
        try {
            horseList = jdbcTemplate.query(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, horse.getName() != null ? "%" + horse.getName() + "%" : "%");
                stmt.setString(2, horse.getDescription() != null ? "%" + horse.getDescription() + "%" : "%");
                stmt.setDate(3, horse.getBirthDate() != null ? horse.getBirthDate() : Date.valueOf("9999-12-31"));
                if (horse.getIsMale() != null) {
                    stmt.setBoolean(4, horse.getIsMale());
                } else {
                    stmt.setString(4, "%");
                }
                if (horse.getBreed() != null && horse.getBreed().getId() != null) {
                    stmt.setLong(5, horse.getBreed().getId());
                } else {
                    stmt.setString(5, "%");
                }
                LOGGER.debug("Query: " + stmt.toString());
                return stmt;
            }, this::mapRow);
        } catch (DataAccessException e){
            throw new PersistenceException("Could not access database while searching for horses.");
        }
//        LOGGER.info("List: "+horseList);
        return horseList;
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
        horse.setBreed(new Breed(resultSet.getLong("breed_id"), resultSet.getString("breed_name")));
        Horse father;
        Horse mother;
        try {
            father = findOneById(resultSet.getLong("father_id"));
            horse.setFather(father);
        } catch (NotFoundException e){
            horse.setFather(new Horse(0L));
        }
        try {
            mother = findOneById(resultSet.getLong("mother_id"));
            horse.setMother(mother);
        } catch (NotFoundException e){
            horse.setMother(new Horse(0L));
        }
        return horse;
    }
}
