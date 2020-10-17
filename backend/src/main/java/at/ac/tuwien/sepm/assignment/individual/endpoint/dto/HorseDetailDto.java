package at.ac.tuwien.sepm.assignment.individual.endpoint.dto;

import java.sql.Date;

public class HorseDetailDto {

    private Long id;
    private String name;
    private String description;
    private Date birthDate;
    private Boolean isMale;
    private BreedDto breed;
    private HorseDto father;
    private HorseDto mother;

    public HorseDetailDto(Long id, String name, String description, Date birthDate, Boolean isMale, BreedDto breed, HorseDto father, HorseDto mother) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.birthDate = birthDate;
        this.isMale = isMale;
        this.breed = breed;
        this.father = father;
        this.mother = mother;
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

    public HorseDto getFather() {
        return father;
    }

    public void setFather(HorseDto father) {
        this.father = father;
    }

    public HorseDto getMother() {
        return mother;
    }

    public void setMother(HorseDto mother) {
        this.mother = mother;
    }
}
