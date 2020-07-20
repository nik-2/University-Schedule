package by.puesosi.schedule.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    List<Subject> subjects;

    @ManyToMany(mappedBy = "groups")
    private List<User> users;

    public Group() {
    }

    public Group(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("name='").append(name).append('\'');
        return sb.toString();
    }
}
