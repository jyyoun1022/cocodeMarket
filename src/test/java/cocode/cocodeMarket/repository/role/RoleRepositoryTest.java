package cocode.cocodeMarket.repository.role;

import cocode.cocodeMarket.entity.member.Role;
import cocode.cocodeMarket.entity.member.RoleType;
import cocode.cocodeMarket.exception.RoleNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class RoleRepositoryTest {
    @Autowired
    RoleRepository roleRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    void createAndReadTest()  { // 1
        // given
        Role role = createRole();

        // when
        roleRepository.save(role);
        clear();

        // then
        Role foundRole = roleRepository.findById(role.getId()).orElseThrow(RoleNotFoundException::new);
        assertThat(foundRole.getId()).isEqualTo(role.getId());
    }

    @Test
    void deleteTest() { // 2
        // given
        Role role = roleRepository.save(createRole());
        clear();

        // when
        roleRepository.delete(role);

        // then
        assertThatThrownBy(() -> roleRepository.findById(role.getId()).orElseThrow(RoleNotFoundException::new))
                .isInstanceOf(RoleNotFoundException.class);
    }

    @Test
    void uniqueRoleTypeTest() { // 3
        // given
        roleRepository.save(createRole());
        clear();

        // when, then
        assertThatThrownBy(() -> roleRepository.save(createRole()))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    private Role createRole() {
        return new Role(RoleType.ROLE_NORMAL);
    }

    private void clear() {
        em.flush();
        em.clear();
    }
}