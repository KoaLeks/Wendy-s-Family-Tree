package at.ac.tuwien.sepm.assignment.individual.unit.endpoint;

import static org.junit.jupiter.api.Assertions.*;
import at.ac.tuwien.sepm.assignment.individual.endpoint.HorseEndpoint;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.GenerationDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseTreeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public abstract class HorseEndpointTestBase {

    @Autowired
    HorseEndpoint horseEndpoint;

    @Test
    @DisplayName("Getting family tree should return a list of HorseTreeDtos based on the number of generations")
    public void gettingFamilyTree_returnsHorseDtoList_BasedOnGenerations() {
        // Generations = 3 --> max number of horses = 2 * generations + 1 = 7
        List<HorseTreeDto> family = horseEndpoint.getHorseFamilyTree(9L, new GenerationDto(3L));
        assertTrue(family.size() <= 7);
    }

    @Test
    @DisplayName("Updating horses sex while it's assigned as a parent to a horse should throw UNPROCESSABLE_ENTITY ResponseStatusException")
    public void updatingHorse_changingSexWhileItsAssignedAsAParent_Throws_UNPROCESSABLE_ENTITY_ResponseStatusException(){
        HorseDto horse = new HorseDto(null, null, null, null, true, null, null, null);
        try {
            horseEndpoint.updateHorse(2L, horse);
        } catch (ResponseStatusException e) {
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, e.getStatus());
        }
    }
}
