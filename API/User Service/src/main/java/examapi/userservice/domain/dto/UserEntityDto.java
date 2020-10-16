package examapi.userservice.domain.dto;

import java.util.Set;

public class UserEntityDto {

    private long id;
    private String username;
    private String password;
    private Set<UserEntityRoleDto> roles;

    public UserEntityDto() {
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

    public Set<UserEntityRoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserEntityRoleDto> roles) {
        this.roles = roles;
    }
}
