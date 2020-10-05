package at.ac.tuwien.sepm.assignment.individual.endpoint;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error saving horse", e);
        } catch (PersistenceException e){
            throw  new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Error saving horse", e);
        }
    }
}
