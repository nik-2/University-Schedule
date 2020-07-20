package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    Group findFirstByName(String name);
}
