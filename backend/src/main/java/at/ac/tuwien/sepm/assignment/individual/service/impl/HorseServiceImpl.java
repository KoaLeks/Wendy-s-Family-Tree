package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.util.Validator;
import ch.qos.logback.core.joran.conditional.IfAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.rmi.ServerException;
import java.util.Collections;
import java.util.LinkedList;
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
        LOGGER.debug("Update: Horse id: {}; Horse values:  name={}, description={},  date={}, isMale={}, breed={}",
            id, horse.getName(), horse.getDescription(), horse.getBirthDate(), horse.getIsMale(), horse.getBreed());
        validator.validateUpdateHorse(id, horse);
        return horseDao.update(id, horse);
    }

    @Override
    public void delete(Long id) throws ValidationException, NotFoundException, PersistenceException {
        LOGGER.trace("delete({})", id);
        validator.checkId(id);
        findOneById(id);
        horseDao.delete(id);
    }

    @Override
    public Horse findOneById(Long id) throws ValidationException {
        LOGGER.trace("findOneById({})", id);
        validator.checkId(id);
        return horseDao.findOneById(id);
    }

    @Override
    public List<Horse> findHorses(Horse horse) throws PersistenceException {
        LOGGER.trace("Search for horses.");
        LOGGER.debug("Search params: name={}, description={}, date={}, isMale={}, breed={}",
            horse.getName(), horse.getDescription(), horse.getBirthDate(), horse.getIsMale(), horse.getBreed());
        return horseDao.findHorses(horse);
    }

    @Override
    public List<Horse> getAll() throws PersistenceException {
        LOGGER.trace("Get all horses.");
        return horseDao.getAll();
    }

    @Override
    public List<Horse> getFamilyTree(Long id, Long generations) throws PersistenceException {
        LOGGER.trace("Get family tree for horse={}, number of generations={}", id, generations);
        validator.validateHorseTree(generations);
        Horse horseRoot = findOneById(id);
        List<Horse> family = new LinkedList<>();
        family.add(horseRoot);
        family.addAll(getTree(horseRoot, generations));
        return generations > 0 ? family : List.of();
    }

    private List<Horse> getTree(Horse root, Long generations) {
        if (generations <= 1) {
            return List.of();
        }
        List<Horse> tree = new LinkedList<>();
        Horse father = root.getFather();
        Horse mother = root.getMother();
        if (father.getId() == 0 && mother.getId() == 0) {
            return tree;
        }
        tree.addAll(horseDao.getParents(father.getId(), mother.getId()));
        if (father.getId() != 0) {
            tree.addAll(getTree(father, generations - 1));
        }
        if (mother.getId() != 0) {
            tree.addAll(getTree(mother, generations - 1));
        }
        return tree;
    }
}
