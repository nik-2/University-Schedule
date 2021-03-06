package by.puesosi.schedule.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * The type Employee.
 */
@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "employee_name")
    private String employeeName;

    @ManyToMany(mappedBy = "employee")
    private List<Subject> subjects;

    @Column(name = "mail")
    private String employeeMail;

    /**
     * Instantiates a new Employee.
     */
    public Employee() {
    }

    /**
     * Instantiates a new Employee.
     *
     * @param employeeName the employee name
     * @param employeeMail the employee mail
     */
    public Employee(String employeeName, String employeeMail){
        this.employeeName = employeeName;
        this.employeeMail = employeeMail;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("employeeName='").append(employeeName).append('\'');
        sb.append(", employeeMail='").append(employeeMail).append('\'');
        return sb.toString();
    }
}
