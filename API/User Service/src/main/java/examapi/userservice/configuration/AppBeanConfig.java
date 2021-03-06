package examapi.userservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import examapi.userservice.innerSecurity.ApiKey;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppBeanConfig {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Bean
    public ApiKey apiKey() {
        return new ApiKey();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


}
