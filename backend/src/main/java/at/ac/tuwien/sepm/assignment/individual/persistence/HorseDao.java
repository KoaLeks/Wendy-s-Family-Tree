package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import java.util.List;

public interface HorseDao {

    /**
     * @param horse to be saved.
     * @return the horse.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    Horse save(Horse horse) throws PersistenceException;

    /**
     * @param id    of the horse to update.
     * @param horse with new values.
     * @return horse with updated values.
     * @throws NotFoundException    will be thrown if the horse could not be found in the database.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    Horse update(Long id, Horse horse) throws NotFoundException, PersistenceException;

    /**
     * @param id of the horse to delete.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    void delete(Long id) throws PersistenceException;

    /**
     * @param id of the horse to find.
     * @return the horse with the specified id.
     * @throws NotFoundException will be thrown if the horse could not be found in the database.
     */
    Horse findOneById(Long id) throws NotFoundException;

    /**
     * @param horse contains the parameter for the search.
     * @return A list of all horses, that fulfil the parameter.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    List<Horse> findHorses(Horse horse) throws PersistenceException;

    /**
     * @param id of parent.
     * @return A list of horse children, where parent horse is either mother or father.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    List<Horse> getChildren(Long id) throws PersistenceException;

    /**
     * @param fatherId id of father.
     * @param motherId id of mother.
     * @return A list of horse parents, where parent horse is either mother or father.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    List<Horse> getParents(Long fatherId, Long motherId) throws PersistenceException;

    /**
     * @return list of all horses in the database.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    List<Horse> getAll() throws PersistenceException;
}
