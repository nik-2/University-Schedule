package by.puesosi.schedule.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * The type City.
 */
@Entity
@Table(name = "city")
@Getter
@Setter
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Transient
    @OneToOne(mappedBy = "city")
    private Group group;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Institution> institutions;

    /**
     * Instantiates a new City.
     */
    public City() {
    }

    /**
     * Instantiates a new City.
     *
     * @param name the name
     */
    public City(String name){
        this.name = name;
    }
}
