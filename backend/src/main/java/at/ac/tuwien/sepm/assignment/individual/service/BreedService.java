package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;

import java.util.List;

public interface BreedService {


    /**
     * Gets the breed with a given ID.
     * @param id of the owner to find.
     * @return the owner with the specified id.
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if the owner could not be found in the system.
     */
    Breed getOneById(Long id);


    /**
     * @return A list that contains all breeds
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     */
    List<Breed> getAll();
}
