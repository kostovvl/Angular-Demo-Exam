package examapi.gateway.service;

import examapi.gateway.domain.user.UserEntity;
import examapi.gateway.innerSecurity.SecurityClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

   private final SecurityClient securityClient;

    public UserDetailsServiceImpl(SecurityClient securityClient) {
        this.securityClient = securityClient;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.securityClient.findByUsername(username);

        String userNameDetail = userEntity.getUsername();
        String passwordDetail = userEntity.getPassword();
        List<GrantedAuthority> roles = userEntity.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRole()))
                .collect(Collectors.toList());

        return new User(userNameDetail, passwordDetail, roles);
    }
}

