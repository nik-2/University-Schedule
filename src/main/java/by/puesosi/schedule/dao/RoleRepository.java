package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * The interface Role repository.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * Find by name set.
     *
     * @param name the name
     * @return the set
     */
    Set<Role> findByName(String name);
}
