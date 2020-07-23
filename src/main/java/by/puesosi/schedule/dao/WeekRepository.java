package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.Week;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Week repository.
 */
public interface WeekRepository extends JpaRepository<Week, Integer> {
    /**
     * Find first by week number week.
     *
     * @param weekNumber the week number
     * @return the week
     */
    Week findFirstByWeekNumber(int weekNumber);
}
