package by.puesosi.schedule.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * The type Group.
 */
@Getter
@Setter
@Entity
@Table(name = "groupa")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @OneToOne
    @JoinColumn(name = "city_id")
    private City city;

    /**
     * The Subjects.
     */
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Subject> subjects;

    @ManyToMany(mappedBy = "groups")
    private List<User> users;

    @PreRemove
    public void removePositions() {
        users.forEach(user -> user.getGroups().remove(this));
    }

    /**
     * Instantiates a new Group.
     */
    public Group() {
    }

    /**
     * Instantiates a new Group.
     *
     * @param name the name
     */
    public Group(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("name='").append(name).append('\'');
        return sb.toString();
    }
}
