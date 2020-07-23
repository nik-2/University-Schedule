package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.City;
import by.puesosi.schedule.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Institution repository.
 */
public interface InstitutionRepository extends JpaRepository<Institution, Integer> {
    /**
     * Find institution by city and name institution.
     *
     * @param city the city
     * @param name the name
     * @return the institution
     */
    Institution findInstitutionByCityAndName(City city, String name);

    /**
     * Find institution by city name list.
     *
     * @param cityName the city name
     * @return the list
     */
    List<Institution> findInstitutionByCityName(String cityName);

    /**
     * Find institution by city name and name institution.
     *
     * @param city        the city
     * @param institution the institution
     * @return the institution
     */
    Institution findInstitutionByCityNameAndName(String city, String institution);
}
