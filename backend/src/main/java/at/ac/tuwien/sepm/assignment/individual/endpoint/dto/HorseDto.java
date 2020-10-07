package at.ac.tuwien.sepm.assignment.individual.endpoint.dto;

import java.sql.Date;
import java.util.Objects;

public class HorseDto {


    private Long id;
    private String name;
    private String description;
    private Date birthDate;
    private boolean isMale;
    private BreedDto breed;

    public HorseDto() {
    }

    public HorseDto(Long id, String name, String description, Date birthDate, boolean isMale, BreedDto breed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.birthDate = birthDate;
        this.isMale = isMale;
        this.breed = breed;
    }

    protected String fieldsString() {
        return "id=" + id +
            ", name='" + name +
            ", description=" + description +
            ", birthDate=" + birthDate +
            ", isMale=" + isMale +
            ", breed=" + breed +
            '\'';
    }

    @Override
    public String toString() {
        return "HorseDto{ " + fieldsString() +" }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HorseDto horseDto = (HorseDto) o;
        return isMale == horseDto.isMale &&
            Objects.equals(id, horseDto.id) &&
            name.equals(horseDto.name) &&
            Objects.equals(description, horseDto.description) &&
            birthDate.equals(horseDto.birthDate) &&
            Objects.equals(breed, horseDto.breed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, birthDate, isMale, breed);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean getIsMale() {
        return isMale;
    }

    public void setIsMale(boolean male) {
        isMale = male;
    }

    public BreedDto getBreed() {
        return breed;
    }

    public void setBreed(BreedDto breed) {
        this.breed = breed;
    }
}
