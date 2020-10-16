package examapi.adminservice.innerSecurity;

public class ApiKey {

    private String key;

    public ApiKey() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        System.out.println("Key" + this.key + "received!");
    }

    public void checkKey(String apiKey) {
        if (!this.key.equals(apiKey)) {
            throw new IllegalArgumentException("Unauthorized!");
        }
    }
}
