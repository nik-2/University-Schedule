package by.puesosi.schedule.service.user;

import by.puesosi.schedule.entity.User;

import java.security.Principal;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Add group schedule.
     *
     * @param user        the user
     * @param city        the city
     * @param institution the institution
     * @param group       the group
     */
    void addGroupSchedule(User user, String city, String institution, String group);

    /**
     * Gets current user.
     *
     * @param principal the principal
     * @return the current user
     */
    User getCurrentUser(Principal principal);

    /**
     * Find user by email user.
     *
     * @param email the email
     * @return the user
     */
    User findUserByEmail(String email);

    /**
     * Find user by username user.
     *
     * @param username the username
     * @return the user
     */
    User findUserByUsername(String username);

    /**
     * Delete group.
     *
     * @param user        the user
     * @param city        the city
     * @param institution the institution
     * @param group       the group
     */
    void deleteGroup(User user, String city, String institution, String group);

    /**
     * Check group availability boolean.
     *
     * @param user        the user
     * @param city        the city
     * @param institution the institution
     * @param group       the group
     * @return the boolean
     */
    boolean checkGroupAvailability(User user, String city, String institution, String group);

    /**
     * Delete group from db boolean.
     *
     * @param city        the city
     * @param institution the institution
     * @param group       the group
     * @return the boolean
     */
    boolean deleteGroupFromDB(String city, String institution, String group);
}
