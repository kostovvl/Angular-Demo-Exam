package examapi.gateway.domain.user;

import java.util.List;

public class UserEntityContainer{

    private List<UserEntity> all;

    public UserEntityContainer() {
    }

    public List<UserEntity> getAll() {
        return all;
    }

    public void setAll(List<UserEntity> all) {
        this.all = all;
    }
}
