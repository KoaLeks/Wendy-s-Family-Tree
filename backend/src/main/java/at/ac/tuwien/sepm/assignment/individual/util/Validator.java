package at.ac.tuwien.sepm.assignment.individual.util;

import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import java.lang.invoke.MethodHandles;
import java.sql.Date;
import java.time.LocalDate;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.persistence.BreedDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final BreedDao breedDao;

    @Autowired
    public Validator(BreedDao breedDao) {
        this.breedDao = breedDao;
    }

    public void validateNewBreed(Breed breed) {
        if (breed.getName() == null || breed.getName().equals("")) {
            throw new ValidationException("Name must be set!");
        }
        if (breed.getName().length() >= 255) {
            throw new ValidationException("Name too long! Please keep it below 255 characters!");
        }
        if (checkIfDuplicateBreedName(breed)) {
            throw new ValidationException("Breed with name: " + breed.getName() + " already exists!");
        }
    }

    public void validateNewHorse(Horse horse) throws ValidationException {
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
    }

    public void validateUpdateHorse(Long id, Horse horse) throws ValidationException {
        checkId(id);
        if (horse.getName() == null || horse.getName().equals("")) {
            throw new ValidationException("Name must be set!");
        }
        if (horse.getBirthDate() == null || !horse.getBirthDate().toLocalDate().isBefore(LocalDate.now().plusDays(1))) {
            throw new ValidationException("Birthdate must be set! Can't be set to future date!");
        }
        if(horse.getIsMale() == null) {
            throw new ValidationException("Sex must be selected!");
        }
    }

    public void validateParentDate(Horse child, Horse parent){
        if (parent == null) {
            return;
        }
        if(child.getBirthDate().toLocalDate().isBefore(parent.getBirthDate().toLocalDate()) ||
            child.getBirthDate().toLocalDate().isEqual(parent.getBirthDate().toLocalDate())){
            throw new ValidationException("Invalid parent! Children can't be older than their parents!");
        }
    }

    public void validateParentsCheckIfSameSex(Horse father, Horse mother) {
        if (father == null || mother == null) {
            return;
        }
        if (father.getIsMale() == mother.getIsMale()) {
            throw new ValidationException("Both parents can't have the same sex!");
        }
    }

    public boolean checkIfDuplicateBreedName(Breed breed){
        Breed duplicate;
        try {
            duplicate = breedDao.getOneByName(breed.getName());
        } catch (NotFoundException e) {
            return false;
        }
        return breed.getName().equalsIgnoreCase(duplicate.getName());
    }

    public void checkId(Long id) {
        if (id < 0) {
            throw new ValidationException("Invalid ID value! ID=" + id);
        }
    }
}
