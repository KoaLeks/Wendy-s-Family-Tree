package at.ac.tuwien.sepm.assignment.individual.unit.persistence;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public abstract class HorseDaoTestBase {
    @Autowired
    HorseDao horseDao;

    @Test
    @DisplayName("Deleting horse with no matching id in the database should throw NotFoundException")
    public void deletingHorse_withNoMatchingID_throwsNotFoundException() {
        assertThrows(NotFoundException.class, () -> horseDao.delete(0L));
    }

    @Test
    @DisplayName("Updating horse should change its values")
    public void updatingHorse_shouldChangeHorseValues() {
        Horse beforeUpdate = horseDao.save(TestData.getNewHorse());
        Horse newValues = TestData.getNewHorse();
        newValues.setName("Mimi");
        newValues.setDescription("new Description");
        newValues.setIsMale(false);
        Horse afterUpdate = horseDao.update(beforeUpdate.getId(), newValues);
        assertNotEquals(beforeUpdate, afterUpdate);
    }
}
