package at.ac.tuwien.sepm.assignment.individual.endpoint.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class SearchDto {

    private String name;
    private String description;
    private LocalDate birthDate;
    private Boolean isMale;
    private Long breedId;

    public SearchDto(String name, String description, LocalDate birthDate, Boolean isMale, Long breedId) {
        this.name = name;
        this.description = description;
        this.birthDate = birthDate;
        this.isMale = isMale;
        this.breedId = breedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchDto searchDto = (SearchDto) o;
        return Objects.equals(name, searchDto.name) &&
            Objects.equals(description, searchDto.description) &&
            Objects.equals(birthDate, searchDto.birthDate) &&
            Objects.equals(isMale, searchDto.isMale) &&
            Objects.equals(breedId, searchDto.breedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, birthDate, isMale, breedId);
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getIsMale() {
        return isMale;
    }

    public void setIsMale(Boolean male) {
        isMale = male;
    }

    public Long getBreedId() {
        return breedId;
    }

    public void setBreedId(Long breedId) {
        this.breedId = breedId;
    }
}
