package at.ac.tuwien.sepm.assignment.individual.unit.endpoint;

import static org.junit.jupiter.api.Assertions.*;
import at.ac.tuwien.sepm.assignment.individual.endpoint.BreedEndpoint;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.BreedDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class BreedEndpointTestBase {

    @Autowired
    BreedEndpoint breedEndPoint;

    @Test
    @DisplayName("Getting all breeds should return a BreedDto list that contains all breeds")
    public void gettingAllBreeds_ReturnsListThatContainsAllBreeds() {
        assertEquals(10, breedEndPoint.getAll().size());
    }

    @Test
    @DisplayName("Saving breed with no name should throw UNPROCESSABLE_ENTITY ResponseStatusException")
    public void savingBreed_WithNoName_Throws_UNPROCESSABLE_ENTITY_ResponseStatusException() {
        BreedDto breedDto = new BreedDto(null, "", "no name");
        try {
            breedEndPoint.saveBreed(breedDto);
        } catch (ResponseStatusException e) {
            assertEquals(e.getStatus(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
