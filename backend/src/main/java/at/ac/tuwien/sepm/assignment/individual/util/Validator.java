package at.ac.tuwien.sepm.assignment.individual.util;

import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.List;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.persistence.BreedDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final BreedDao breedDao;
    private final HorseDao horseDao;

    @Autowired
    public Validator(BreedDao breedDao, HorseDao horseDao) {
        this.breedDao = breedDao;
        this.horseDao = horseDao;
    }

    public void validateHorseTree(Long generations) {
        LOGGER.trace("validateHorseTree({})", generations);
        if (generations == null || generations <= 0) {
            throw new ValidationException("Number of generations must be set! Numbers below 1 won't display anything!");
        }

    }

    public void validateNewBreed(Breed breed) {
        LOGGER.trace("validateNewBreed({})", breed);
        if (breed.getId() != null) {
            throw new ValidationException("Please don't assign IDs manually. IDs are automatically assigned!");
        }
        if (breed.getName() == null || breed.getName().equals("")) {
            throw new ValidationException("Name must be set!");
        }
        if (breed.getName().length() > 255) {
            throw new ValidationException("Name too long! Please keep it below 255 characters!");
        }
        if (breed.getDescription() != null && breed.getDescription().length() > 255) {
            throw new ValidationException("Name too long! Please keep it below 255 characters!");
        }
        if (checkIfDuplicateBreedName(breed)) {
            throw new ServiceException("Breed with name: " + breed.getName() + " already exists!");
        }
    }

    public void validateNewHorse(Horse horse) throws ValidationException {
        LOGGER.trace("validateNewHorse({})", horse);
        if (horse.getId() != null) {
            throw new ValidationException("Please don't assign IDs manually. IDs are automatically assigned!");
        }
        validateHorseValues(horse);
    }

    public void validateUpdateHorse(Long id, Horse horse) throws ValidationException {
        LOGGER.trace("validateUpdateHorse({})", horse);
        checkId(id);
        List<Horse> children = horseDao.getChildren(id);
        validateHorseValues(horse);
        if (checkIfSexChanged(id, horse) && !children.isEmpty()) {
            throw new ServiceException("Horse still has children! Please make sure the horse has no children, before changing its sex!");
        }
        if (!children.isEmpty() && children.get(0).getBirthDate().toLocalDate().isBefore(horse.getBirthDate().toLocalDate().plusDays(1))) {
            throw new ValidationException("Invalid Date! Parents can't be younger than their oldest child! Oldest child name: " + children.get(0).getName() + ", birthdate: " + children.get(0).getBirthDate());
        }
    }

    private void validateHorseValues(Horse horse) {
        LOGGER.trace("validateHorseValues({})", horse);
        if (horse.getName() == null || horse.getName().equals("")) {
            throw new ValidationException("Name must be set!");
        }
        if (horse.getName().length() > 255) {
            throw new ValidationException("Name too long! Please keep it below 255 characters!");
        }
        if (horse.getDescription() != null && horse.getDescription().length() > 255) {
            throw new ValidationException("Description too long! Please keep it below 255 characters!");
        }
        if (horse.getBirthDate() == null || !horse.getBirthDate().toLocalDate().isBefore(LocalDate.now().plusDays(1))) {
            throw new ValidationException("Birthdate must be set! Can't be set to future date!");
        }
        if(horse.getIsMale() == null) {
            throw new ValidationException("Sex must be selected!");
        }
        if (!checkIfBreedExists(horse.getBreed())) {
            throw new ServiceException("Horse breed does not match breed in database!");
        }
        validateParents(horse);
    }

    private boolean checkIfSexChanged(Long id, Horse horse) {
        LOGGER.trace("checkIfSexChanged({})", horse);
        Horse horseCheck = horseDao.findOneById(id);
        return !(horseCheck.getIsMale() == horse.getIsMale());
    }

    private void validateParentDate(Horse child, Horse parent){
        LOGGER.trace("validateParentDate({}, {})", child, parent);
        if (parent == null) {
            return;
        }
        if (child.getBirthDate().toLocalDate().isBefore(parent.getBirthDate().toLocalDate().plusDays(1))) {
            throw new ValidationException("Invalid date! Children can't be older than their parents!");
        }
    }

    private void validateParentsCheckIfSameSex(Horse father, Horse mother) {
        LOGGER.trace("validateParentsCheckIfSameSex({}, {})", father, mother);
        if (father == null || mother == null) {
            return;
        }
        if (father.getIsMale() == mother.getIsMale()) {
            throw new ValidationException("Both parents can't have the same sex!");
        }
    }

    public void validateParents(Horse horse) {
        LOGGER.trace("validateParents({})", horse);
        Horse father;
        Horse mother;
        try {
            father = horse.getFather() != null && horse.getFather().getId() != null && horse.getFather().getId() != 0 ? horseDao.findOneById(horse.getFather().getId()) : null;
        } catch (NotFoundException e) {
            throw new ServiceException("Invalid Parent. Father with id= " + horse.getFather().getId() + " does not exist.");
        }
        try {
            mother = horse.getMother() != null && horse.getMother().getId() != null  && horse.getMother().getId() != 0 ? horseDao.findOneById(horse.getMother().getId()) : null;
        } catch (NotFoundException e) {
            throw new ServiceException("Invalid Parent. Mother with id= " + horse.getFather().getId() + " does not exist.");
        }
        validateParentsCheckIfSameSex(father, mother);
        validateParentDate(horse, father);
        validateParentDate(horse, mother);
    }

    public boolean checkIfBreedExists(Breed breed) {
        LOGGER.trace("checkIfBreedExists({})", breed);
        if (breed == null || breed.getId() == null || breed.getId() == 0) {
            return true;
        }
        Breed breedCheck = breedDao.getOneById(breed.getId());
        return breedCheck.equals(breed);
    }

    public boolean checkIfDuplicateBreedName(Breed breed){
        LOGGER.trace("checkIfDuplicateBreedName({})", breed);
        Breed duplicate;
        try {
            duplicate = breedDao.getOneByName(breed.getName());
        } catch (NotFoundException e) {
            return false;
        }
        return breed.getName().equalsIgnoreCase(duplicate.getName());
    }

    public void checkId(Long id) {
        LOGGER.trace("checkId({})", id);
        if (id == null || id < 0) {
            throw new ValidationException("Invalid ID value! ID=" + id);
        }
    }
}
