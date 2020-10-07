package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HorseDao horseDao;
    private final Validator validator;

    @Autowired
    public HorseServiceImpl(HorseDao horseDao, Validator validator) {
        this.horseDao = horseDao;
        this.validator = validator;
    }

    @Override
    public Horse save(Horse horse) throws PersistenceException, ValidationException {
        LOGGER.trace("save({})", horse);
        LOGGER.debug("Save: Horse values: " + horse);
        validator.validateNewHorse(horse);
        return horseDao.save(horse);
    }

    @Override
    public Horse update(Long id, Horse horse) throws PersistenceException, NotFoundException, ValidationException {
        LOGGER.trace("update({})", id);
        LOGGER.debug("Update: Horse id: {}; Horse values:  name={}, description={},  date={}, isMale={}, breedId={}",
            id, horse.getName(), horse.getDescription(), horse.getBirthDate(), horse.getIsMale(), horse.getBreed());
        validator.validateUpdateHorse(id, horse);
        return horseDao.update(id, horse);
    }

    @Override
    public List<Horse> getAll() throws PersistenceException {
        LOGGER.trace("Get all horses.");
        return horseDao.getAll();
    }
}
