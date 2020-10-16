package examapi.userservice.domain.dto;

public class UserEntityRoleDto {

    private long id;
    private String role;

    public UserEntityRoleDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
