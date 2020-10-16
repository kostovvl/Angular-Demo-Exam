package examapi.userservice.service;

import examapi.userservice.domain.dto.UserEntityDto;
import examapi.userservice.domain.entity.UserEntity;
import examapi.userservice.domain.entity.UserEntityRole;
import examapi.userservice.repository.UserEntityRepository;
import examapi.userservice.repository.UserEntityRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;
    private final UserEntityRoleRepository userEntityRoleRepository;
    private final ModelMapper mapper;

    public UserEntityService(UserEntityRepository userEntityRepository,
                             UserEntityRoleRepository userEntityRoleRepository, ModelMapper mapper) {
        this.userEntityRepository = userEntityRepository;
        this.userEntityRoleRepository = userEntityRoleRepository;
        this.mapper = mapper;
    }

    public boolean repositoryEmpty() {
        return this.userEntityRepository.count() == 0;
    }

    public UserEntityDto registerUser(UserEntityDto newUser) {
        UserEntity userUser = this.mapper.map(newUser, UserEntity.class);
        userUser.setRegisteredOn(LocalDateTime.now());

        UserEntityRole roleUser = new UserEntityRole("USER");
        roleUser.setUser(userUser);

        if (this.userEntityRepository.count() == 0) {
            UserEntityRole roleRootAdmin = new UserEntityRole("ROOT_ADMIN");
            roleRootAdmin.setUser(userUser);

            UserEntityRole roleAdmin = new UserEntityRole("ADMIN");
            roleAdmin.setUser(userUser);
            userUser.setRoles(Set.of(roleUser, roleAdmin, roleRootAdmin));
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

    public List<UserEntityDto> findAllWithoutRootAdmin() {
        return this.userEntityRepository.findAll()
                .stream()
                .filter(u -> !u.getUsername().equals("Admin"))
                .map(u -> this.mapper.map(u, UserEntityDto.class))
                .collect(Collectors.toList());
    }

    public void updateToAdmin(long id) {
        UserEntity forUpdate = this.userEntityRepository.getOne(id);

        UserEntityRole roleAdmin = new UserEntityRole("ADMIN");
        roleAdmin.setUser(forUpdate);

        forUpdate.addRole(roleAdmin);

        this.userEntityRepository.saveAndFlush(forUpdate);
    }

    @Transactional
    public void downgradeToUser(long id) {
        UserEntity forDowngrade = this.userEntityRepository.getOne(id);

        Set<UserEntityRole>roles = forDowngrade.getRoles();

        roles.stream().filter(r -> r.getRole().equals("ADMIN"))
                .forEach(r -> this.userEntityRoleRepository.deleteById(r.getId()));

        roles = roles.stream().filter(r -> r.getRole().equals("USER"))
                .collect(Collectors.toSet());

        forDowngrade.setRoles(roles);

        this.userEntityRepository.saveAndFlush(forDowngrade);
    }
}
