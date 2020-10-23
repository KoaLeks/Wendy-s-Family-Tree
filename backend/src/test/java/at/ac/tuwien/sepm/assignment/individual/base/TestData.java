package at.ac.tuwien.sepm.assignment.individual.base;


import at.ac.tuwien.sepm.assignment.individual.entity.Breed;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.sql.Date;

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
        return new Breed( 1L);
    }

    static Breed getNewBreed(Long id, String name, String description) {
        return new Breed(id, name, description);
    }

    static Breed getNewBreed( String name, String description) {
        return new Breed(null, name, description);
    }

    static Breed getNewBreedWithId() {
        return new Breed(1L, "Breed");
    }

    /**
     * Horse Data
     */
    static Horse getNewHorse(){
        return new Horse(12L, "Johan", "", Date.valueOf("2020-06-22"), true, null, new Horse(88L), new Horse(77L));
    }
    static Horse getNewHorse(Horse father, Horse mother){
        return new Horse(null, "Johan", "", Date.valueOf("2020-06-22"), true, null, father, mother);
    }

}
