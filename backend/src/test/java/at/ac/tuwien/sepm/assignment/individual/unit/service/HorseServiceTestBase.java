package at.ac.tuwien.sepm.assignment.individual.unit.service;

import static org.junit.jupiter.api.Assertions.*;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class HorseServiceTestBase {

    @Autowired
    HorseService horseService;

    @Test
    @DisplayName("Saving horse with two same sex parents should throw ValidationException")
    public void savingHorse_withSameSexParents_ThrowsValidationException() {
        Horse father = horseService.findOneById(1L);
        assertThrows(ValidationException.class, () -> horseService.save(TestData.getNewHorse(father, father)));
    }

    @Test
    @DisplayName("Saving horse with valid parameter and two valid parents should return the horse")
    public void savingHorse_withValidValuesAndValidParents_ReturnHorse() {
        Horse father = horseService.findOneById(1L);
        Horse mother = horseService.findOneById(2L);
        Horse beforeSave = TestData.getNewHorse(father, mother);
        Horse afterSave = horseService.save(beforeSave);
        assertEquals(beforeSave, afterSave);
    }
}
