package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface City repository.
 */
public interface CityRepository extends JpaRepository<City, Integer> {
    /**
     * Find city by name city.
     *
     * @param name the name
     * @return the city
     */
    City findCityByName(String name);
}
