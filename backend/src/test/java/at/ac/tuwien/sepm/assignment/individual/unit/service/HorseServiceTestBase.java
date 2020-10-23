package at.ac.tuwien.sepm.assignment.individual.unit.service;

import static org.junit.jupiter.api.Assertions.*;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.invoke.MethodHandles;


public abstract class HorseServiceTestBase {

    @Autowired
    HorseService horseService;

    @Test
    @DisplayName("Saving horse with two same sex parents should throw ValidationException")
    public void savingHorse_withSameSexParents_ThrowsValidationException() {
        Horse father = horseService.findOneById(1L);
        Horse father2 = horseService.findOneById(3L);
        assertThrows(ValidationException.class, () -> horseService.save(TestData.getNewHorse(father, father2)));
    }

    @Test
    @DisplayName("Saving horse with valid parameter and two valid parents should return the horse")
    public void savingHorse_withValidValuesAndValidParents_ReturnHorse() {
        Horse father = horseService.findOneById(5L);
        Horse mother = horseService.findOneById(6L);
        Horse beforeSave = TestData.getNewHorse(father, mother);
        Horse afterSave = horseService.save(beforeSave);
        assertEquals(beforeSave, afterSave);
    }
}
