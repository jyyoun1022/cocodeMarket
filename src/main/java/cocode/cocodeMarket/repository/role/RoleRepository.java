package cocode.cocodeMarket.repository.role;

import cocode.cocodeMarket.entity.member.Role;
import cocode.cocodeMarket.entity.member.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}