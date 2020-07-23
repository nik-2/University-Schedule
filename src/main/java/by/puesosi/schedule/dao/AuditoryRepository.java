package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.Auditory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Auditory repository.
 */
public interface AuditoryRepository extends JpaRepository<Auditory, Integer> {
    /**
     * Find first by name auditory.
     *
     * @param name the name
     * @return the auditory
     */
    Auditory findFirstByName(String name);
}
