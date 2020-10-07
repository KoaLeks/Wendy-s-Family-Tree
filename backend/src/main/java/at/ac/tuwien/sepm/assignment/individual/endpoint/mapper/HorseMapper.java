package at.ac.tuwien.sepm.assignment.individual.endpoint.mapper;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.BreedDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class HorseMapper {

    public HorseDto entityToDto(Horse horse) {
        return new HorseDto(horse.getId(), horse.getName(), horse.getDescription(), horse.getBirthDate(),
            horse.getIsMale(), horse.getBreed() != null ? new BreedDto(horse.getBreed().getId(), horse.getBreed().getName()) : null);
    }

    public Horse dtoToEntity(HorseDto horseDto) {
        return new Horse(horseDto.getId(), horseDto.getName(), horseDto.getDescription(), horseDto.getBirthDate(),
            horseDto.getIsMale(), horseDto.getBreed() != null ? new Breed(horseDto.getBreed().getId(), horseDto.getBreed().getName()) : null);
    }
    public List<HorseDto> entityToDtoList(List<Horse> horseList) {
        List<HorseDto> dtoList = new LinkedList<>();
        for (Horse horse: horseList) {
            dtoList.add(new HorseDto(horse.getId(), horse.getName(), horse.getDescription(), horse.getBirthDate(),
                horse.getIsMale(), new BreedDto(horse.getBreed().getId(), horse.getBreed().getName())));
        }
        return dtoList;
    }
}