package examapi.userservice.innerSecurity;

public class ApiKey {

    private String key;

    public ApiKey() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        System.out.println("Key " + key + " received!");
    }
}
