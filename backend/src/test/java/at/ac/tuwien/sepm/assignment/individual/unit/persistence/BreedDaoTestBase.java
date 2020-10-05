package at.ac.tuwien.sepm.assignment.individual.unit.persistence;

import static org.junit.jupiter.api.Assertions.*;

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
            () -> breedDao.getOneById(1L));
    }

}
