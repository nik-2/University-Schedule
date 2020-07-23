package by.puesosi.schedule.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * The type Day of week.
 */
@Getter
@Setter
@Entity
@Table(name = "day_of_week")
public class DayOfWeek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL)
    private List<Subject> subjects;

    /**
     * Instantiates a new Day of week.
     */
    public DayOfWeek() {
    }

    /**
     * Instantiates a new Day of week.
     *
     * @param name the name
     */
    public DayOfWeek(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("name='").append(name).append('\'');
        return sb.toString();
    }
}
