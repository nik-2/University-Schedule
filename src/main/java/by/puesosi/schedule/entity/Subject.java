package by.puesosi.schedule.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "start")
    private String startLessonTime;

    @Column(name = "end")
    private String endLessonTime;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Week> weekNumber;

    @Column(name = "subject_name")
    private String subjectName;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Employee> employee;

    @Column(name = "note")
    private String note;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Auditory> auditory;

    @Column(name = "subgroup")
    private String subgroup;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "day_id")
    private DayOfWeek day;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "institution_id")
    private Institution institution;

    public Subject() {
    }

    public Subject(String startLessonTime, String endLessonTime, String subjectName, String note, String subgroup){
        this.startLessonTime = startLessonTime;
        this.endLessonTime = endLessonTime;
        this.subjectName = subjectName;
        this.note = note;
        this.subgroup = subgroup;
    }



}
