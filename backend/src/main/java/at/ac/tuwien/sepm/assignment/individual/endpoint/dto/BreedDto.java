package at.ac.tuwien.sepm.assignment.individual.endpoint.dto;

import java.util.Objects;

public class BreedDto {
    private Long id;
    private String name;

    public BreedDto() {
    }

    public BreedDto(String name) {
        this.name = name;
    }

    public BreedDto(Long id, String name) {
        this(name);
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
        return "id = " + id + ", name='" + name + '\'';
    }

    @Override
    public String toString() {
        return "BreedDto{ " + fieldsString() + " }";
    }
}
