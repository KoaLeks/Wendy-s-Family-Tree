package at.ac.tuwien.sepm.assignment.individual.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.BreedService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BreedServiceTestBase {

    @Autowired
    BreedService breedService;

    @Test
    @DisplayName("Saving breed with predefined ID should throw ValidationException")
    public void savingBreed_withPredefinedID_throwsValidationException(){
        assertThrows(ValidationException.class, () -> breedService.save(TestData.getNewBreed(1L, "test", "")));
    }

    @Test
    @DisplayName("Saving two breeds with the same name should throw ServiceException")
    public void savingBreed_withSameName_throwsServiceException(){
        assertThrows(ServiceException.class, () -> breedService.save(TestData.getNewBreed("Morgan", "")));
    }
}
