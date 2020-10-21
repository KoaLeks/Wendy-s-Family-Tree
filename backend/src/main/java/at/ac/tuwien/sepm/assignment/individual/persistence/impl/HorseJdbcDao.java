package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.BreedDao;
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
    private final BreedDao breedDao;

    @Autowired
    public HorseJdbcDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, BreedDao breedDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.breedDao = breedDao;
    }

    @Override
    public Horse save(Horse horse) throws PersistenceException {
        LOGGER.trace("Save horse with name: " + horse.getName());
        final String sql = "INSERT INTO " + TABLE_NAME + " (ID, NAME, DESCRIPTION, BIRTH_DATE, IS_MALE, BREED_ID, FATHER_ID, MOTHER_ID)" +
            " VALUES (null, ?, ?, ?, ?, ?, ?, ?)";

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
                if (horse.getBreed() != null && horse.getBreed().getId() != null && horse.getBreed().getId() != 0) {
                    stmt.setLong(5, horse.getBreed().getId());
//                    stmt.setString(6, horse.getBreed().getName());
                } else {
                    stmt.setNull(5, Types.BIGINT);
//                    stmt.setString(6, null);
                }
                if (horse.getFather() != null && horse.getFather().getId() != null) {
                    stmt.setLong(6, horse.getFather().getId());
                } else {
                    stmt.setNull(6, Type.LONG);
                }
                if (horse.getMother() != null && horse.getMother().getId() != null) {
                    stmt.setLong(7, horse.getMother().getId());
                } else {
                    stmt.setNull(7, Type.LONG);
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
            "BIRTH_DATE=COALESCE(?, BIRTH_DATE), IS_MALE=COALESCE(?, IS_MALE), BREED_ID=?, FATHER_ID=?, MOTHER_ID=? WHERE ID=?";

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
                if (horse.getBreed() != null && horse.getBreed().getId() != null && horse.getBreed().getId() != 0) {
                    stmt.setLong(5, horse.getBreed().getId());
//                    stmt.setString(6, horse.getBreed().getName());
                } else {
                    stmt.setNull(5, Type.LONG);
//                    stmt.setString(6, null);
                }
                if (horse.getFather() != null && horse.getFather().getId() != null) {
                    stmt.setLong(6, horse.getFather().getId());
                } else {
                    stmt.setNull(6, Type.LONG);
                }
                if (horse.getMother() != null && horse.getMother().getId() != null) {
                    stmt.setLong(7, horse.getMother().getId());
                } else {
                    stmt.setNull(7, Type.LONG);
                }
                stmt.setLong(8, id);
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
        Horse horse = findOneById(id);
        String horseParent =  horse.getIsMale() ?  " SET FATHER_ID=0 WHERE FATHER_ID=?" : " SET MOTHER_ID=0 WHERE MOTHER_ID=?";
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE ID=?; UPDATE " + TABLE_NAME + horseParent;
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setLong(1, id);
                stmt.setLong(2, id);
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
        LOGGER.trace("Search for horses with parameter: name: {}, description: {}, birthDate: {}, isMale: {}, breed: {}",
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

    @Override
    public List<Horse> getChildren(Long id) throws PersistenceException {
        LOGGER.trace("Get children for horse with id {}", id);
        Horse parent = findOneById(id);
        String horseParent =  parent.getIsMale() ?  "FATHER_ID=?" : "MOTHER_ID=?";
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + horseParent + "  ORDER BY BIRTH_DATE ASC";
        List<Horse> horseList;
        try {
            horseList = jdbcTemplate.query(connection -> {
                PreparedStatement stmt = connection.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setLong(1, parent.getId());
                LOGGER.debug("Query: " + stmt.toString());
                return stmt;
            }, this::mapRow);
        } catch (DataAccessException e){
            throw new PersistenceException("Could not get children for horse with id: " + parent.getId());
        }
        return horseList;
    }

    @Override
    public List<Horse> getParents(Long fatherId, Long motherId) throws PersistenceException {
        LOGGER.trace("Get parents for horse with ids: father={}, mother={}", fatherId, motherId);
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID IN (?,?)";
        List<Horse> horseList;
        try {
            horseList = jdbcTemplate.query(connection -> {
                PreparedStatement stmt = connection.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS);
                if (fatherId != null) {
                    stmt.setLong(1, fatherId);
                } else {
                    stmt.setNull(1, Type.LONG);
                }
                if (motherId != null) {
                    stmt.setLong(2, motherId);
                } else {
                    stmt.setNull(2, Type.LONG);
                }
                LOGGER.debug("Query: " + stmt.toString());
                return stmt;
            }, this::mapRow);
        } catch (DataAccessException e){
            throw new PersistenceException("Could not get parents with ids: father=" + fatherId + ", mother=" + motherId);
        }
        return horseList;
    }

    private Horse mapRow(ResultSet resultSet, int i) throws SQLException {
        final Horse horse = new Horse();
        horse.setId(resultSet.getLong("id"));
        horse.setName(resultSet.getString("name"));
        horse.setDescription(resultSet.getString("description"));
        horse.setBirthDate(Date.valueOf(resultSet.getTimestamp("birth_date").toLocalDateTime().toLocalDate()));
        horse.setIsMale(resultSet.getBoolean("is_male"));
        horse.setBreed(breedDao.getOneById(resultSet.getLong("breed_id")));
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
