package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.Auditory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoryRepository extends JpaRepository<Auditory, Integer> {
    Auditory findFirstByName(String name);
}
