package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Day of week repository.
 */
public interface DayOfWeekRepository extends JpaRepository<DayOfWeek, Integer> {
    /**
     * Find first by name day of week.
     *
     * @param name the name
     * @return the day of week
     */
    DayOfWeek findFirstByName(String name);
}
