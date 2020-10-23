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
    public Horse save(Horse horse) {
        LOGGER.trace("save({})", horse);
        LOGGER.debug("Save: Horse values: " + horse);
        validator.validateNewHorse(horse);
        return horseDao.save(horse);
    }

    @Override
    public Horse update(Long id, Horse horse) {
        LOGGER.trace("update({})", id);
        LOGGER.debug("Update: Horse id: {}; Horse values:  name={}, description={},  date={}, isMale={}, breed={}",
            id, horse.getName(), horse.getDescription(), horse.getBirthDate(), horse.getIsMale(), horse.getBreed());
        validator.validateUpdateHorse(id, horse);
        return horseDao.update(id, horse);
    }

    @Override
    public void delete(Long id) {
        LOGGER.trace("delete({})", id);
        validator.checkId(id);
        findOneById(id);
        horseDao.delete(id);
    }

    @Override
    public Horse findOneById(Long id)  {
        LOGGER.trace("findOneById({})", id);
        validator.checkId(id);
        return horseDao.findOneById(id);
    }

    @Override
    public List<Horse> findHorses(Horse horse) throws PersistenceException {
        LOGGER.trace("findHorses({})", horse);
        LOGGER.debug("Search params: name={}, description={}, date={}, isMale={}, breed={}",
            horse.getName(), horse.getDescription(), horse.getBirthDate(), horse.getIsMale(), horse.getBreed());
        return horseDao.findHorses(horse);
    }

    @Override
    public List<Horse> getAll() throws PersistenceException {
        LOGGER.trace("getAll()");
        return horseDao.getAll();
    }

    @Override
    public List<Horse> getFamilyTree(Long id, Long generations) {
        LOGGER.trace("getFamilyTree({}, {})", id, generations);
        validator.validateHorseTree(generations);
        Horse horseRoot = findOneById(id);
        List<Horse> family = new LinkedList<>();
        family.add(horseRoot);
        family.addAll(getTree(horseRoot, generations));
        return generations > 1 ? family : List.of();
    }

    private List<Horse> getTree(Horse root, Long generations) {
        LOGGER.trace("getTree({}, {})", root, generations);
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
