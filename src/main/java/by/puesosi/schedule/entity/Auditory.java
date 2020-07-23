package by.puesosi.schedule.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * The type Auditory.
 */
@Getter
@Setter
@Entity
@Table(name = "auditory")
public class Auditory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "auditory")
    private List<Subject> subjects;

    /**
     * Instantiates a new Auditory.
     */
    public Auditory() {
    }

    /**
     * Instantiates a new Auditory.
     *
     * @param name the name
     */
    public Auditory(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("name='").append(name).append('\'');
        return sb.toString();
    }
}
