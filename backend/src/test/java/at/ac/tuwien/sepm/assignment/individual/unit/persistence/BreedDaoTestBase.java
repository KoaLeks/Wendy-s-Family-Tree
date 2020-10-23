package at.ac.tuwien.sepm.assignment.individual.unit.persistence;

import static org.junit.jupiter.api.Assertions.*;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.BreedDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BreedDaoTestBase {

    @Autowired
    BreedDao breedDao;

    @Test
    @DisplayName("Finding breed by non-existing ID should throw NotFoundException")
    public void findingBreedById_nonExisting_shouldThrowNotFoundException() {
        assertThrows(NotFoundException.class,
            () -> breedDao.getOneById(99L));
    }

    @Test
    @DisplayName("Saving breed should return the exact same breed")
    public void savingNewBreed_shouldReturnSameBreed() {
        Breed beforeSave = TestData.getNewBreed(99L, "Appaloosa", "Spanische Rasse");
        Breed afterSave = breedDao.save(beforeSave);
        assertEquals(beforeSave, afterSave);
    }

}
