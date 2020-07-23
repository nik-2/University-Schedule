package by.puesosi.schedule.service.registration;

import by.puesosi.schedule.entity.User;


/**
 * The interface Registration service.
 */
public interface RegistrationService {
    /**
     * Check new user data string.
     *
     * @param user the user
     * @return the string
     */
    String checkNewUserData(User user);
}
