package at.ac.tuwien.sepm.assignment.individual.base;


import at.ac.tuwien.sepm.assignment.individual.entity.Breed;

public interface TestData {

    /**
     * URI Data
     */
    String BASE_URL = "http://localhost:";
    String HORSE_URL = "/horses";
    String BREEDS_URL = "/breeds";

    /**
     * Breed Data
     */
    static Breed getNewBreed() {
        return new Breed("Breed");
    }

    static Breed getNewBreed(String name) {
        return new Breed(name);
    }

    static Breed getNewBreedWithId() {
        return new Breed(1L, "Breed");
    }


}
