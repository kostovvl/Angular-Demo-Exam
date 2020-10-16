package examapi.userservice.repository;

import examapi.userservice.domain.entity.UserEntityRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRoleRepository extends JpaRepository<UserEntityRole, Long> {
}
