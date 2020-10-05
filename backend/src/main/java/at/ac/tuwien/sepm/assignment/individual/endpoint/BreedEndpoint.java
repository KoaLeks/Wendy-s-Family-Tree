package at.ac.tuwien.sepm.assignment.individual.endpoint;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.BreedDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.BreedMapper;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.service.BreedService;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(BreedEndpoint.BASE_URL)
public class BreedEndpoint {

    static final String BASE_URL = "/breeds";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final BreedService breedService;
    private final BreedMapper breedMapper;

    @Autowired
    public BreedEndpoint(BreedService breedService, BreedMapper breedMapper) {
        this.breedService = breedService;
        this.breedMapper = breedMapper;
    }

    @GetMapping(value = "/{id}")
    public BreedDto getOneById(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/{}", id);
        try {
            return breedMapper.entityToDto(breedService.getOneById(id));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading breed", e);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BreedDto> getAll() {
        LOGGER.info("GET " + BASE_URL );
        try {
            return breedMapper.entityToDtoList(breedService.getAll());
        } catch (PersistenceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error getting breeds", e);
        }
    }
}
