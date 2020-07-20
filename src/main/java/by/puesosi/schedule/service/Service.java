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

public interface Service extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    void checkBSUIRSchedule();

    HashMap<String, List<Subject>> findGroupSchedule(String group, String city, String institution);

    List<City> findCity();

    List<Institution> findInstitutions(String city);

    boolean checkCity(String city);

    boolean checkInstitution(String city, String institution);
}
