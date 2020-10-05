package at.ac.tuwien.sepm.assignment.individual.entity;

import java.sql.Date;
import java.util.Objects;

public class Horse {

    private Long id;
    private String name;
    private String description;
    private Date birthDate;
    private boolean isMale;
    private Long breedId;

    public Horse() {
    }

    public Horse(Long id, String name, String description, Date birthDate, boolean isMale, Long breedId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.birthDate = birthDate;
        this.isMale = isMale;
        this.breedId = breedId;
    }

    protected String fieldsString() {
        return "id=" + id +
            ", name='" + name +
            ", description=" + description +
            ", birthDate=" + birthDate +
            ", isMale=" + isMale +
            ", breed=" + breedId +
            '\'';
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
            Objects.equals(breedId, horse.breedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, birthDate, isMale, breedId);
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

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public Long getBreedId() {
        return breedId;
    }

    public void setBreed(Long breedId) {
        this.breedId = breedId;
    }
}
