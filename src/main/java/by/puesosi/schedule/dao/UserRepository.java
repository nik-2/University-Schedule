package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface User repository.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Find by username user.
     *
     * @param username the username
     * @return the user
     */
    User findByUsername(String username);

    /**
     * Find first by username user.
     *
     * @param username the username
     * @return the user
     */
    User findFirstByUsername(String username);

    /**
     * Find first by email user.
     *
     * @param email the email
     * @return the user
     */
    User findFirstByEmail(String email);

    /**
     * Find first by username or email user.
     *
     * @param username the username
     * @param email    the email
     * @return the user
     */
    User findFirstByUsernameOrEmail(String username, String email);

    /**
     * Find by email user.
     *
     * @param email the email
     * @return the user
     */
    User findByEmail(String email);
}
