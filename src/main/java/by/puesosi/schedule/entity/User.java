package by.puesosi.schedule.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * The type User.
 */
@Entity
@Getter
@Setter
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Please, provide username")
    @Pattern(regexp = "[A-Za-z_\\d]{4,15}", message = "Use only numbers, letters and _")
    @Size(min = 4, max = 15, message = "Minimum chars - 4. Maximum chars - 15")
    @Column(name = "username")
    private String username;

    @Email(message = "Please, input correct email, for example: abc@xxx.xx")
    @NotEmpty(message = "Please, provide email")
    @Size(min = 5, max = 30, message = "Incorrect email. Please, use more then 4 chars and less 31")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Please, provide first name")
    @Pattern(regexp = "[A-Z][a-z]+(-[A-Z][a-z]+)?", message = "Please, input correct first name, for example: Username or Username-Username")
    @Size(min = 2, max = 25, message = "Incorrect first name. Use more then 1 char and less then 26")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Please, provide last name")
    @Pattern(regexp = "[A-Z][a-z]+(-[A-Z][a-z]+)?", message = "Please, input correct last name, for example: Username or Username-Username")
    @Size(min = 2, max = 25, message = "Incorrect last name. Use more then 1 char and less then 26")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Please, provide password")
    @Pattern(regexp = "[A-Za-z_\\d]{4,15}", message = "Use only numbers, letters and _")
    @Size(min = 4, max = 15, message = "Incorrect password. Please, use more then 3 chars and less then 16")
    @Column(name = "password")
    private String password;

    @Column(name = "google_name")
    private String googleName;

    @ManyToMany
    private List<Group> groups;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Column(name = "last_visit")
    private LocalDateTime lastVisit;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param username  the username
     * @param email     the email
     * @param firstName the first name
     * @param lastName  the last name
     */
    public User(String username, String email, String firstName, String lastName){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
