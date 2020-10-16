package examapi.gateway.domain.user;

import java.util.List;

public class UserEntity {

    private long id;
    private String username;
    private String password;
    private List<UserEntityRole> roles;

    public UserEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserEntityRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserEntityRole> roles) {
        this.roles = roles;
    }
}
