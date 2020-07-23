package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.City;
import by.puesosi.schedule.entity.Group;
import by.puesosi.schedule.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Group repository.
 */
public interface GroupRepository extends JpaRepository<Group, Integer> {
    /**
     * Find first by name and institution and city group.
     *
     * @param name        the name
     * @param institution the institution
     * @param city        the city
     * @return the group
     */
    Group findFirstByNameAndInstitutionAndCity(String name, Institution institution, City city);

    /**
     * Find first by institution name and city name and name group.
     *
     * @param institutionName the institution name
     * @param cityName        the city name
     * @param groupName       the group name
     * @return the group
     */
    Group findFirstByInstitution_NameAndCity_NameAndName(String institutionName, String cityName, String groupName);

}
