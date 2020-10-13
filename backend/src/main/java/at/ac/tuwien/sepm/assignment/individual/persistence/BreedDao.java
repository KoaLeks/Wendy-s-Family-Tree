package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;

import java.util.List;

public interface BreedDao {

    /**
     * Get the breed with given ID.
     *
     * @param id of the breed to find.
     * @return the breed with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException   will be thrown if the breed could not be found in the database.
     */
    Breed getOneById(Long id);

    /**
     * Get the breed with given name.
     *
     * @param name of the breed to find.
     * @return the breed with the specified name.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException   will be thrown if the breed could not be found in the database.
     */
    Breed getOneByName(String name);


    /**
     * Get the breed with given ID.
     *
     * @param breed to be saved.
     * @return the breed.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException   will be thrown if the breed could not be found in the database.
     */
    Breed save(Breed breed);

    /**
     * @return A list that contains all breeds
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     */
    List<Breed> getAll();

}
