package at.ac.tuwien.sepm.assignment.individual.endpoint.dto;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.sql.Date;
import java.util.Objects;

public class HorseDto {


    private Long id;
    private String name;
    private String description;
    private Date birthDate;
    private Boolean isMale;
    private BreedDto breed;
    private Long fatherId;
    private Long motherId;

    public HorseDto() {
    }

    public HorseDto(Horse horse) {
        this(horse.getId(), horse.getName(), horse.getDescription(), horse.getBirthDate(), horse.getIsMale(), new BreedDto(horse.getBreed()), horse.getFather().getId(), horse.getMother().getId());
    }

    public HorseDto(Long id) {
        this.id = id;
    }

    public HorseDto(Long id, String name, String description, Date birthDate, Boolean isMale, BreedDto breed, Long fatherId, Long motherId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.birthDate = birthDate;
        this.isMale = isMale;
        this.breed = breed;
        this.fatherId = fatherId;
        this.motherId = motherId;
    }

    protected String fieldsString() {
        return "id=" + id +
            ", name='" + name +
            ", description=" + description +
            ", birthDate=" + birthDate +
            ", isMale=" + isMale +
            ", breed=" + breed +
            ", fatherId=" + fatherId +
            ", motherId=" + motherId +
            '\'';
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
            Objects.equals(breed, horseDto.breed) &&
            Objects.equals(fatherId, horseDto.fatherId) &&
            Objects.equals(motherId, horseDto.motherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, birthDate, isMale, breed, fatherId, motherId);
    }

    @Override
    public String toString() {
        return "HorseDto{ " + fieldsString() +" }";
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

    public Long getMotherId() {
        return motherId;
    }

    public void setMotherId(Long motherId) {
        this.motherId = motherId;
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

    public Boolean getIsMale() {
        return isMale;
    }

    public void setIsMale(Boolean male) {
        isMale = male;
    }

    public BreedDto getBreed() {
        return breed;
    }

    public void setBreed(BreedDto breed) {
        this.breed = breed;
    }
}
