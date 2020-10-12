package at.ac.tuwien.sepm.assignment.individual.endpoint.dto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class SearchDto {

    @Nullable
    private String name;
    @Nullable
    private String description;
    @Nullable
    private String birthDate;
    @Nullable
    private String isMale;
    @Nullable
    private String breedId;

    public SearchDto(String name, String description, String birthDate, String isMale, String breedId) {
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getIsMale() {
        return isMale;
    }

    public void setIsMale(String male) {
        isMale = male;
    }

    public String getBreedId() {
        return breedId;
    }

    public void setBreedId(String breedId) {
        this.breedId = breedId;
    }
}
