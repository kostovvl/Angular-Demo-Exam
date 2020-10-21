package examapi.userservice.unitTest;

import examapi.userservice.domain.dto.UserEntityDto;
import examapi.userservice.domain.entity.UserEntity;
import examapi.userservice.domain.entity.UserEntityRole;
import examapi.userservice.repository.UserEntityRepository;
import examapi.userservice.repository.UserEntityRoleRepository;
import examapi.userservice.service.UserEntityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceTest {

    private UserEntityService userEntityService;

    private UserEntityDto userEntityDtoAdmin;
    private UserEntityDto userEntityDtoRootAdmin;

    private UserEntityRoleRepository userEntityRoleRepository;
    private UserEntity userEntityAdmin;
    private UserEntity userEntityRootAdmin;
    private UserEntityRole rootAdminRole;

    @Mock
    UserEntityRepository userEntityRepository;


    @BeforeEach()
    public void setUp() {
        //Initialize Service
        this.userEntityService = new UserEntityService(this.userEntityRepository, this.userEntityRoleRepository, new ModelMapper());

        //Initialize Entities
        this.userEntityDtoRootAdmin = new UserEntityDto();
        this.userEntityDtoRootAdmin.setUsername("Admin");
        this.userEntityDtoRootAdmin.setPassword("123456");

        this.userEntityRootAdmin = new UserEntity();
        this.userEntityRootAdmin.setUsername("Admin");
        this.userEntityRootAdmin.setPassword("123456");

        this.rootAdminRole = new UserEntityRole("ROOT_ADMIN");
        this.rootAdminRole.setUser(userEntityRootAdmin);
        this.userEntityRootAdmin.setRoles(Set.of(rootAdminRole));

        this.userEntityDtoAdmin = new UserEntityDto();
        this.userEntityDtoAdmin.setUsername("SecondaryAdmin");
        this.userEntityDtoAdmin.setPassword("123456");

        this.userEntityAdmin = new UserEntity();
        this.userEntityAdmin.setUsername("SecondaryAdmin");
        this.userEntityAdmin.setPassword("123456");
    }

    @Test
    public void shouldReturnUser() {
        //arrange
        when(this.userEntityRepository.findByUsername("SecondaryAdmin")).thenReturn(Optional.of(this.userEntityAdmin));

        //act
        UserEntityDto result = this.userEntityService.findByUsername("SecondaryAdmin");

        //assert
        Assertions.assertEquals(this.userEntityDtoAdmin.getUsername(), result.getUsername());
    }

    @Test
    public void should_Return_All() {
        //arrange
        List<UserEntity>users = new ArrayList<>();
        users.add(userEntityRootAdmin);
        users.add(userEntityAdmin);

        when(this.userEntityRepository.findAll()).thenReturn(users);

        //act
        List<UserEntityDto>result = this.userEntityService.findAllWithoutRootAdmin();

        //assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(this.userEntityDtoAdmin.getUsername(), result.get(0).getUsername());

    }

}
