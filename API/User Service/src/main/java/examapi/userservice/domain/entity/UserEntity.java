package examapi.userservice.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {

    private long id;
    private String username;
    private String password;
    private LocalDateTime registeredOn;
    private Set<UserEntityRole> roles;

    public UserEntity() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "usernanme", unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "registered_on")
    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }

    @OneToMany(mappedBy = "user", targetEntity = UserEntityRole.class,
    fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<UserEntityRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserEntityRole> roles) {
        this.roles = roles;
    }

    public void addRole(UserEntityRole newRole) {
        this.roles.add(newRole);
    }
}
