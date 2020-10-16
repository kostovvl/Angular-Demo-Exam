package examapi.gateway.domain.user;

public class UserEntityRole {

    private long id;
    private String role;

    public UserEntityRole() {
    }

    public UserEntityRole(String role) {
        this.role = role;
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
