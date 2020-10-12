package at.ac.tuwien.sepm.assignment.individual.endpoint.mapper;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.BreedDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.SearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Component
public class HorseMapper {

    public HorseDto entityToDto(Horse horse) {
        return new HorseDto(horse.getId(), horse.getName(), horse.getDescription(), horse.getBirthDate(),
            horse.getIsMale(), horse.getBreed() != null ? new BreedDto(horse.getBreed().getId(), horse.getBreed().getName()) : null,
            horse.getFather().getId(), horse.getMother().getId());
    }

    public Horse dtoToEntity(HorseDto horseDto) {
        return new Horse(horseDto.getId(), horseDto.getName(), horseDto.getDescription(), horseDto.getBirthDate(),
            horseDto.getIsMale(), horseDto.getBreed() != null ? new Breed(horseDto.getBreed().getId(), horseDto.getBreed().getName()) : null,
            new Horse(horseDto.getFatherId()), new Horse(horseDto.getMotherId()));
    }

    public Horse searchDtoToEntity(SearchDto searchDto) {
       return new Horse(null,
            (searchDto.getName() != null && !searchDto.getName().equals("null")) ? searchDto.getName() : null,
            (searchDto.getDescription() != null && !searchDto.getDescription().equals("null")) ? searchDto.getDescription() : null,
            (searchDto.getBirthDate() != null && !searchDto.getBirthDate().equals("null") && !searchDto.getBirthDate().equals("")) ? Date.valueOf(searchDto.getBirthDate()) : Date.valueOf(LocalDate.now()),
            (searchDto.getIsMale() != null && !searchDto.getIsMale().equals("null") && !searchDto.getIsMale().equals("")) ? Boolean.valueOf(searchDto.getIsMale()) : null,
            (searchDto.getBreedId() != null && !searchDto.getBreedId().equals("null") && !searchDto.getBreedId().equals("")) ? new Breed(Long.valueOf(searchDto.getBreedId())) : null,
            null, null);
    }

    public List<HorseDto> entityToDtoList(List<Horse> horseList) {
        List<HorseDto> dtoList = new LinkedList<>();
        for (Horse horse: horseList) {
            dtoList.add(new HorseDto(horse.getId(), horse.getName(), horse.getDescription(), horse.getBirthDate(),
                horse.getIsMale(), horse.getBreed() != null ? new BreedDto(horse.getBreed().getId(), horse.getBreed().getName()) : null,
                horse.getFather() != null ? horse.getFather().getId() : 0,
                horse.getMother() != null ? horse.getMother().getId() : 0)
            );
        }
        return dtoList;
    }
}