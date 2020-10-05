package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;

import java.util.List;

public interface BreedDao {

    /**
     * Get the breed with given ID.
     * @param id of the owner to find.
     * @return the owner with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException   will be thrown if the owner could not be found in the database.
     */
    Breed getOneById(Long id);

    /**
     * @return A list that contains all breeds
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     */
    List<Breed> getAll();

}
