package examapi.gateway.security;

import examapi.gateway.innerSecurity.SecurityClient;
import examapi.gateway.service.UserDetailsServiceImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import static examapi.gateway.security.SecurityConstraints.ALL_CATEGORIES_URL;
import static examapi.gateway.security.SecurityConstraints.SIGN_UP_URL;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //if you want to pre-authorize specific method
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityClient securityClient;

    public WebSecurity(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder, SecurityClient securityClient) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;

        this.securityClient = securityClient;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(SIGN_UP_URL, ALL_CATEGORIES_URL).permitAll()
                .antMatchers( "/posts/update/**",
                        "/posts/by_category/**",
                        "/posts/details/**",
                        "/posts/delete/**").hasAnyAuthority("USER")
                .antMatchers("/comments/getForPost/**",
                        "/comments/update/**").hasAnyAuthority("USER")
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().csrf().disable()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), securityClient))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}