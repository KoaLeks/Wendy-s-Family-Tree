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
    public Horse save(Horse horse) {
        LOGGER.trace("save({})", horse);
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
                if (horse.getBreed() != null && horse.getBreed().getId() != null && horse.getBreed().getId() != 0) {
                    stmt.setLong(5, horse.getBreed().getId());
                } else {
                    stmt.setNull(5, Types.BIGINT);
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

        if (!keyHolder.getKeyList().isEmpty()) {
            horse.setId(((Number) keyHolder.getKeys().get("id")).longValue());
        }

        return horse;
    }

    @Override
    public Horse update(Long id, Horse horse) {
        LOGGER.trace("update({}, {})", id, horse);
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
                if (horse.getBreed() != null && horse.getBreed().getId() != null && horse.getBreed().getId() != 0) {
                    stmt.setLong(5, horse.getBreed().getId());
                } else {
                    stmt.setNull(5, Type.LONG);
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

        if (keyHolder.getKeyList().isEmpty()) {
            throw new NotFoundException("Could not find horse with id: " + id);
        }
        return horse;
    }

    @Override
    public void delete(Long id) {
        LOGGER.trace("delete({})", id);
        Horse horse = findOneById(id);
        String horseParent =  horse.getIsMale() ?  " SET FATHER_ID=0 WHERE FATHER_ID=?" : " SET MOTHER_ID=0 WHERE MOTHER_ID=?";
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE ID=?; UPDATE " + TABLE_NAME + horseParent;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setLong(1, id);
                stmt.setLong(2, id);
                LOGGER.debug("Query: " + stmt.toString());
                return stmt;
            }, keyHolder);
        } catch (DataAccessException e){
            throw new PersistenceException("Could not find horse with id: " + id);
        }
        if (keyHolder.getKeyList().isEmpty()) {
            throw new NotFoundException("Could not find horse with id: " + id);
        }
    }

    @Override
    public Horse findOneById(Long id) {
        LOGGER.trace("findOneById({})", id);
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ID=?";
        List<Horse> horses = jdbcTemplate.query(sql, new Object[] { id }, this::mapRow);
        if (horses.isEmpty()) {
            throw new NotFoundException("Could not find horse with id " + id);
        }
        return horses.get(0);
    }

    @Override
    public List<Horse> findHorses(Horse horse) {
        LOGGER.trace("findHorses({})", horse);
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
    public List<Horse> getAll() {
        LOGGER.trace("getAll()");
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
    public List<Horse> getChildren(Long id) {
        LOGGER.trace("getChildren({})", id);
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
    public List<Horse> getParents(Long fatherId, Long motherId) {
        LOGGER.trace("getParents({}, {})", fatherId, motherId);
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
        Breed breed;
        try {
            breed = breedDao.getOneById(resultSet.getLong("breed_id"));
            horse.setBreed(breed);
        } catch (NotFoundException e) {
            horse.setBreed(new Breed(0L));
        }
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
