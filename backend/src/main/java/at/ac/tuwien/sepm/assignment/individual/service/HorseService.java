package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;

import java.io.IOException;

public interface HorseService {

    /**
     * Saves a horse in the database. Returns the object if successful.
     *
     * @param horse to be saved in the database
     * @throws PersistenceException will be thrown if something goes wrong during data processing.
     * @throws ValidationException  will be thrown if the horse has invalid values.
     */
    Horse save(Horse horse) throws PersistenceException, ValidationException;

}
