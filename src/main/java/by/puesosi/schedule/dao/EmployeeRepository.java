package by.puesosi.schedule.dao;

import by.puesosi.schedule.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findFirstByEmployeeName(String name);
}
