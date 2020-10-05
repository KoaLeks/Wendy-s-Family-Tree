package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;

public interface HorseDao {

    /**
     * @param horse to be saved.
     * @return the horse.
     * @throws PersistenceException will be thrown if something goes wrong during the database access.
     */
    Horse save(Horse horse) throws PersistenceException;

}
