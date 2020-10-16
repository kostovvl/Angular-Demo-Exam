package examapi.gateway.innerSecurity;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class KeyGenerator {

    private final ApiKey apiKey;


    public KeyGenerator(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    public void generateKey() {
        String newKey = generateRandomString();
        this.apiKey.setKey(newKey);
    }

    private String generateRandomString() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        int length = 7;

        for(int i = 0; i < length; i++) {

            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }

     return  sb.toString();
    }
}
