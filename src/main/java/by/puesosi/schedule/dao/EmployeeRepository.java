package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Employee repository.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    /**
     * Find first by employee name employee.
     *
     * @param name the name
     * @return the employee
     */
    Employee findFirstByEmployeeName(String name);
}
