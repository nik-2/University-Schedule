package by.puesosi.schedule.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "week")
public class Week {

    @Id
    @Column(name = "id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "week_number")
    private int weekNumber;

    @ManyToMany(mappedBy = "weekNumber")
    private List<Subject> subjects;

    public Week() {
    }

    public Week(int weekNumber){
        this.weekNumber = weekNumber;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("weekNumber=").append(weekNumber);
        return sb.toString();
    }
}
