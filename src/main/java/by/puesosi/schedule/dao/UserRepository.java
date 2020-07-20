package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findFirstByUsername(String username);

    User findFirstByEmail(String email);

    User findFirstByUsernameOrEmail(String username, String email);

    User findByEmail(String email);
}
