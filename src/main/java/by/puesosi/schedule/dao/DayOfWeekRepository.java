package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayOfWeekRepository extends JpaRepository<DayOfWeek, Integer> {
    DayOfWeek findFirstByName(String name);
}
