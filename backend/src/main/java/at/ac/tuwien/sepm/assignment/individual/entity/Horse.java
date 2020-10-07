package at.ac.tuwien.sepm.assignment.individual.entity;

import java.sql.Date;
import java.util.Objects;

public class Horse {

    private Long id;
    private String name;
    private String description;
    private Date birthDate;
    private boolean isMale;
    private Breed breed;

    public Horse() {
    }

    public Horse(Long id, String name, String description, Date birthDate, boolean isMale, Breed breed) {
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
            "', description='" + description +
            "', birthDate='" + birthDate +
            "', isMale=" + isMale +
            ", breed=" + breed;
    }

    @Override
    public String toString() {
        return "Horse{ " + fieldsString() +" }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horse horse = (Horse) o;
        return isMale == horse.isMale &&
            Objects.equals(id, horse.id) &&
            name.equals(horse.name) &&
            Objects.equals(description, horse.description) &&
            birthDate.equals(horse.birthDate) &&
            Objects.equals(breed, horse.breed);
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

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }
}
