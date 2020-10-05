package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.BreedDao;
import java.lang.invoke.MethodHandles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BreedJdbcDao implements BreedDao {

    private static final String TABLE_NAME = "Breed";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public BreedJdbcDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Breed getOneById(Long id) {
        LOGGER.trace("getOneById({})", id);
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        List<Breed> breeds = jdbcTemplate.query(sql, new Object[] { id }, this::mapRow);

        if (breeds.isEmpty()) throw new NotFoundException("Could not find breed with id " + id);

        return breeds.get(0);
    }

    @Override
    public List<Breed> getAll() { LOGGER.trace("Get all horses");
        String sql = "SELECT * FROM " + TABLE_NAME;

        List<Breed> breedList;
        try {
            breedList = jdbcTemplate.query(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                LOGGER.debug("Query: " + stmt.toString());
                return stmt;
            }, this::mapRow);
            return breedList;
        } catch (DataAccessException e){
            throw new PersistenceException("Could not get breeds.");
        }
    }

    private Breed mapRow(ResultSet resultSet, int i) throws SQLException {
        final Breed breed = new Breed();
        breed.setId(resultSet.getLong("id"));
        breed.setName(resultSet.getString("name"));
        return breed;
    }
}
