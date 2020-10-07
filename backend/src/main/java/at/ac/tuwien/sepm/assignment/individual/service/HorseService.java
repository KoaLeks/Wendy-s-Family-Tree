package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;

import java.io.IOException;
import java.util.List;

public interface HorseService {

    /**
     * Saves a horse in the database. Returns the object if successful.
     *
     * @param horse to be saved in the database
     * @throws PersistenceException will be thrown if something goes wrong during data processing.
     * @throws ValidationException  will be thrown if the horse has invalid values.
     */
    Horse save(Horse horse) throws PersistenceException, ValidationException;

    /**
     * Updates specific horse, with the updated values given by horse.
     *
     * @param id    of the horse to update.
     * @param horse with the new values.
     * @return horse with updated values.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     * @throws ValidationException  will be thrown if the horse has invalid values.
     * @throws NotFoundException    will be thrown if the horse could not be found in the system.
     */
    Horse update(Long id, Horse horse) throws PersistenceException, NotFoundException, ValidationException;

    /**
     * Deletes the horse with the given id from the database
     *
     * @param id of the horse to delete.
     * @throws ValidationException  will be thrown if the id value is invalid
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    void delete(Long id) throws ValidationException, PersistenceException;

    /**
     * @return list of all horses in the database.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    List<Horse> getAll() throws PersistenceException;
}
