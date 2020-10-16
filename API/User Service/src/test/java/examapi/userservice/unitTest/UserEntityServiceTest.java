package examapi.userservice.unitTest;

import examapi.userservice.domain.dto.UserEntityDto;
import examapi.userservice.domain.entity.UserEntity;
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

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceTest {

    private UserEntityService userEntityService;
    private UserEntityDto userEntityDtoAdmin;
    private UserEntityDto userEntityRoleUser;
    private UserEntityRoleRepository userEntityRoleRepository;
    private UserEntity userEntityAdmin;
    private UserEntity userEntityUser;

    @Mock
    UserEntityRepository userEntityRepository;


    @BeforeEach()
    public void setUp() {
        //Initialize Service
        this.userEntityService = new UserEntityService(this.userEntityRepository, this.userEntityRoleRepository, new ModelMapper());

        //Initialize Entities
        this.userEntityDtoAdmin = new UserEntityDto();
        this.userEntityDtoAdmin.setUsername("Admin");
        this.userEntityDtoAdmin.setPassword("123456");

        this.userEntityAdmin = new UserEntity();
        this.userEntityAdmin.setUsername("Admin");
        this.userEntityAdmin.setPassword("123456");

    }

    @Test
    public void shouldReturnUser() {
        //arrange
        when(this.userEntityRepository.findByUsername("Admin")).thenReturn(Optional.of(this.userEntityAdmin));

        //act
        UserEntityDto result = this.userEntityService.findByUsername("Admin");

        //assert
        Assertions.assertEquals(this.userEntityDtoAdmin.getUsername(), result.getUsername());
    }

}
