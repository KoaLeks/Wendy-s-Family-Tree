package at.ac.tuwien.sepm.assignment.individual.endpoint.dto;

import java.util.Date;

public class HorseTreeDto {
    private Long id;
    private String name;
    private Date birthDate;
    private Long fatherId;
    private Long motherId;

    public HorseTreeDto() {
    }

    public HorseTreeDto(Long id, String name, Date birthDate, Long fatherId, Long motherId) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.fatherId = fatherId;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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
}
