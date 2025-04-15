package oauthgoogle.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import oauthgoogle.api.entity.Role;
import oauthgoogle.api.enums.AppRole;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);

}