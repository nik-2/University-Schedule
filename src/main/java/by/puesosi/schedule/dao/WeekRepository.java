package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.Week;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekRepository extends JpaRepository<Week, Integer> {
    Week findFirstByWeekNumber(int weekNumber);
}
