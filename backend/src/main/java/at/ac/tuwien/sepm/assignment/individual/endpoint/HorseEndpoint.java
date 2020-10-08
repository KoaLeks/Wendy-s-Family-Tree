package at.ac.tuwien.sepm.assignment.individual.endpoint;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(HorseEndpoint.BASE_URL)
public class HorseEndpoint {

    static final String BASE_URL = "/horses";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HorseService horseService;
    private final HorseMapper horseMapper;

    @Autowired
    public HorseEndpoint(HorseService horseService, HorseMapper horseMapper) {
        this.horseService = horseService;
        this.horseMapper = horseMapper;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HorseDto saveHorse(@RequestBody HorseDto horse){
        LOGGER.info("POST " + BASE_URL + "/");
        try{
            Horse horseEntity = horseMapper.dtoToEntity(horse);
            return horseMapper.entityToDto(horseService.save(horseEntity));
        } catch (ValidationException e) {
            LOGGER.error("Error saving horse: " + e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error saving horse: " + e.getMessage(), e);
        } catch (PersistenceException e){
            LOGGER.error("Error saving horse: " + e);
            throw  new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error saving horse: " + e.getMessage(), e);
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HorseDto updateHorse(@PathVariable("id") Long id, @RequestBody HorseDto horse){
        LOGGER.info("PUT " + BASE_URL + "/" + id);
        try {
            Horse horseEntity = horseMapper.dtoToEntity(horse);
            return horseMapper.entityToDto(horseService.update(id, horseEntity));
        } catch (PersistenceException e) {
            LOGGER.error("Error updating horse: " + e);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error updating horse: " + e.getMessage(), e);
        } catch (ValidationException e) {
            LOGGER.error("Error updating horse: " + e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating horse: " + e.getMessage(), e);
        } catch (NotFoundException e) {
            LOGGER.error("Error updating horse: " + e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error updating horse: " + e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteHorse(@PathVariable("id") Long id) {
        LOGGER.info("Delete " + BASE_URL + "/" + id);
        try {
            horseService.delete(id);
        } catch (PersistenceException e) {
            LOGGER.error("Error deleting horse: " + e);
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error deleting horse: " + e.getMessage(), e);
        } catch (ValidationException e) {
            LOGGER.error("Error deleting horse: " + e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error deleting horse: " + e.getMessage(), e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error deleting horse: " + e.getMessage(), e);
        }

    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HorseDto getOneById(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/{}", id);
        try {
            return horseMapper.entityToDto(horseService.findOneById(id));
        } catch (NotFoundException e) {
            LOGGER.error("Error loading horse with id: " + id + e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading horse: " + e.getMessage());
        } catch (ValidationException e) {
            LOGGER.error("Error loading horse with id: " + id + e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error during reading horse: " + e.getMessage());
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<HorseDto> getAllHorses(){
        LOGGER.info("GET All " + BASE_URL + "/" + "");
        try {
            return horseMapper.entityToDtoList(horseService.getAll());
        }catch (PersistenceException e) {
            LOGGER.error("Error loading horses: " + e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Error getting horse list: ", e);
        }
    }
}
