package at.ac.tuwien.sepm.assignment.individual.entity;

import java.util.Objects;

public class Breed {
    private Long id;
    private String name;
    private String description;

    public Breed() {
    }

    public Breed(Long id) {
        this.id = id;
    }

//    public Breed(String name) {
//        this.name = name;
//    }

    public Breed(Long id, String name) {
        this(id);
        this.name = name;
    }

    public Breed(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Breed breed = (Breed) o;
        return id.equals(breed.id) &&
            name.equals(breed.name) &&
            Objects.equals(description, breed.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    protected String fieldsString() {
        return "id=" + id + ", name='" + name + ", description='" + description + '\'';
    }

    @Override
    public String toString() {
        return "Breed{ " + fieldsString() +" }";
    }
}
