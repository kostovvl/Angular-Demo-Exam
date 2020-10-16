package examapi.contentservice.domain.dto;

public abstract class BaseDto {

    private long id;

    public BaseDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
