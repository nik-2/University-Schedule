package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Set<Role> findByName(String name);
}
