package at.ac.tuwien.sepm.assignment.individual.endpoint.mapper;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.BreedDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class BreedMapper {

    public BreedDto entityToDto(Breed breed) {
        return new BreedDto(breed.getId(), breed.getName());
    }

    public Breed entityToDto(BreedDto breedDto) {
        return new Breed(breedDto.getId(), breedDto.getName());
    }


    public List<BreedDto> entityToDtoList(List<Breed> breeds) {
        List<BreedDto> dtoList = new LinkedList<>();
        for (Breed breed: breeds) {
            dtoList.add(new BreedDto(breed.getId(), breed.getName()));
        }
        return dtoList;
    }

}
