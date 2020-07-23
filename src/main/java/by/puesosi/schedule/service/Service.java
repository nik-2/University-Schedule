package by.puesosi.schedule.service;


import by.puesosi.schedule.entity.City;
import by.puesosi.schedule.entity.Group;
import by.puesosi.schedule.entity.Institution;
import by.puesosi.schedule.entity.Subject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.List;

/**
 * The interface Service.
 */
public interface Service extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    /**
     * Check bsuir schedule.
     */
    void checkBSUIRSchedule();

    /**
     * Find group schedule hash map.
     *
     * @param group       the group
     * @param city        the city
     * @param institution the institution
     * @return the hash map
     */
    HashMap<String, List<Subject>> findGroupSchedule(String group, String city, String institution);

    /**
     * Find city list.
     *
     * @return the list
     */
    List<City> findCity();

    /**
     * Find institutions list.
     *
     * @param city the city
     * @return the list
     */
    List<Institution> findInstitutions(String city);

    /**
     * Check city boolean.
     *
     * @param city the city
     * @return the boolean
     */
    boolean checkCity(String city);

    /**
     * Check institution boolean.
     *
     * @param city        the city
     * @param institution the institution
     * @return the boolean
     */
    boolean checkInstitution(String city, String institution);

    /**
     * Find groups list.
     *
     * @return the list
     */
    List<Group> findGroups();

    /**
     * Find group boolean.
     *
     * @param groupName       the group name
     * @param cityName        the city name
     * @param institutionName the institution name
     * @return the boolean
     */
    boolean findGroup(String groupName, String cityName, String institutionName);
}
