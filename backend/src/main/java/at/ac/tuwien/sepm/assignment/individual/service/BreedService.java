package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;

import java.util.List;

public interface BreedService {


    /**
     * Gets the breed with a given ID.
     * @param id of the owner to find.
     * @return the owner with the specified id.
     * @throws NotFoundException if the owner could not be found in the system.
     */
    Breed getOneById(Long id);

    /**
     * Saves a breed in the database. Returns the object if successful.
     *
     * @param breed to be saved in the database
     * @return the saved breed
     * @throws PersistenceException will be thrown if something goes wrong during data processing.
     * @throws ValidationException  will be thrown if the horse has invalid values.
     * @throws ServiceException     will be thrown if the breed has valid values that could cause a conflict.
     */
    Breed save(Breed breed);

    /**
     * @return A list that contains all breeds
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     */
    List<Breed> getAll();
}
