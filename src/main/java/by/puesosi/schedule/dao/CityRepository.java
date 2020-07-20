package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {
    City findCityByName(String name);
}
