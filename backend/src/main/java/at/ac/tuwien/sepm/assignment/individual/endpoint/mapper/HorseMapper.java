package at.ac.tuwien.sepm.assignment.individual.endpoint.mapper;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class HorseMapper {

    public HorseDto entityToDto(Horse horse) {
        return new HorseDto(horse.getId(), horse.getName(), horse.getDescription(), horse.getBirthDate(), horse.getIsMale(), horse.getBreedId());
    }
    public Horse dtoToEntity(HorseDto horseDto) {
        return new Horse(horseDto.getId(), horseDto.getName(), horseDto.getDescription(), horseDto.getBirthDate(), horseDto.getIsMale(), horseDto.getBreedId());
    }

    public List<HorseDto> entityToDtoList(List<Horse> horseList) {
        List<HorseDto> dtoList = new LinkedList<>();
        for (Horse horse: horseList) {
            dtoList.add(new HorseDto(horse.getId(), horse.getName(), horse.getDescription(), horse.getBirthDate(), horse.getIsMale(), horse.getBreedId()));
        }
        return dtoList;
    }
}