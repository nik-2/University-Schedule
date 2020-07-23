package by.puesosi.schedule.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * The type Institution.
 */
@Entity
@Table(name = "institution")
@Getter
@Setter
public class Institution {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Transient
    @OneToOne(mappedBy = "institution")
    private Group group;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL)
    private List<Subject> subjects;

    /**
     * Instantiates a new Institution.
     */
    public Institution() {
    }

    /**
     * Instantiates a new Institution.
     *
     * @param name the name
     */
    public Institution(String name){
        this.name = name;
    }
}
