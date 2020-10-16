package examapi.userservice.domain.entity;

import examapi.userservice.domain.dto.UserEntityDto;

import java.util.List;

public class AllUsers {

    private List<UserEntityDto> all;

    public AllUsers() {
    }

    public AllUsers(List<UserEntityDto> all) {
        this.all = all;
    }

    public List<UserEntityDto> getAll() {
        return all;
    }

    public void setAll(List<UserEntityDto> all) {
        this.all = all;
    }
}
