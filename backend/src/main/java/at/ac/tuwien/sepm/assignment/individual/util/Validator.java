package at.ac.tuwien.sepm.assignment.individual.util;

import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import java.lang.invoke.MethodHandles;
import java.sql.Date;
import java.time.LocalDate;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());



    public void validateNewBreed(Breed breed) {
    }

    public void validateNewHorse(Horse horse) throws ValidationException {
        if (horse.getName() == null || horse.getName().equals("")) {
            throw new ValidationException("Name must be set!");
        }
        if (horse.getBirthDate() == null || !horse.getBirthDate().before(Date.valueOf(LocalDate.now()))) {
            throw new ValidationException("Birth date must be set! Can't be set to future date!");
        }
    }

}
