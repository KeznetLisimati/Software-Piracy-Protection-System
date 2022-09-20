package co.zw.santech.onlinepayments.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // @Id
    private String username;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(nullable = false, unique = true)
    private String password;
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    public User(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.username = user.getUsername();
        this.roles = user.getRoles();
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }


    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPassword() {
        return this.password;
    }


    public List<Role> getRoles() {
        return this.roles;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setRoles(final List<Role> roles) {
        this.roles = roles;
    }

    public String toString() {
        return "User(username=" + this.getUsername() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", password=" + this.getPassword()  + ", roles=" + this.getRoles() + ")";
    }
}
