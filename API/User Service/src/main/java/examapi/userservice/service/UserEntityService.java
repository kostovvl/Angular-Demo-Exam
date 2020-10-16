package examapi.userservice.service;

import examapi.userservice.domain.dto.UserEntityDto;
import examapi.userservice.domain.entity.UserEntity;
import examapi.userservice.domain.entity.UserEntityRole;
import examapi.userservice.repository.UserEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;
    private final ModelMapper mapper;

    public UserEntityService(UserEntityRepository userEntityRepository, ModelMapper mapper) {
        this.userEntityRepository = userEntityRepository;
        this.mapper = mapper;
    }

    public UserEntityDto registerUser(UserEntityDto newUser) {
        UserEntity userUser = this.mapper.map(newUser, UserEntity.class);
        userUser.setRegisteredOn(LocalDateTime.now());

        UserEntityRole roleUser = new UserEntityRole("USER");
        roleUser.setUser(userUser);

        if (this.userEntityRepository.count() == 0) {
            UserEntityRole roleAdmin = new UserEntityRole("ADMIN");
            roleAdmin.setUser(userUser);
            userUser.setRoles(Set.of(roleUser, roleAdmin));
        } else {
            userUser.setRoles(Set.of(roleUser));
        }
        return this.mapper.map(this.userEntityRepository.saveAndFlush(userUser), UserEntityDto.class);
    }

    public UserEntityDto findByUsername(String username) {

        UserEntity userEntity = this.userEntityRepository.findByUsername(username)
                .orElseThrow(UnsupportedOperationException::new);
        return this.mapper.map(userEntity, UserEntityDto.class);
    }
}
