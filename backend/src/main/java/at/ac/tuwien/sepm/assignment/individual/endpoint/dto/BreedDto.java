package at.ac.tuwien.sepm.assignment.individual.endpoint.dto;

import java.util.Objects;

public class BreedDto {
    private Long id;
    private String name;
    private String description;



    public BreedDto() {
    }

    public BreedDto(Long id) {
        this.id = id;
    }

    public BreedDto(String name) {
        this.name = name;
    }

    public BreedDto(Long id, String name) {
        this(id);
        this.name = name;
    }

    public BreedDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BreedDto breedDto = (BreedDto) o;
        return Objects.equals(id, breedDto.id) &&
            Objects.equals(name, breedDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    private String fieldsString() {
        return "id = " + id + ", name='" + name + ", description='" + description + '\'';
    }

    @Override
    public String toString() {
        return "BreedDto{ " + fieldsString() + " }";
    }
}
