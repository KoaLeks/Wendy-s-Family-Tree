package at.ac.tuwien.sepm.assignment.individual.endpoint.dto;

public class GenerationDto {
    private Long generations;

    public GenerationDto() {    }


    public GenerationDto(Long generations) {
        this.generations = generations;
    }

    public Long getGenerations() {
        return generations;
    }

    public void setGenerations(Long generations) {
        this.generations = generations;
    }
}
