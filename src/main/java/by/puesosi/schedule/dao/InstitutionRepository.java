package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.City;
import by.puesosi.schedule.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstitutionRepository extends JpaRepository<Institution, Integer> {
    Institution findInstitutionByCityAndName(City city, String name);

    List<Institution> findInstitutionByCityName(String cityName);

    Institution findInstitutionByCityNameAndName(String city, String institution);
}
