package cocode.cocodeMarket.common;

import cocode.cocodeMarket.entity.member.Member;
import cocode.cocodeMarket.entity.member.RoleType;
import cocode.cocodeMarket.exception.RoleNotFoundException;
import cocode.cocodeMarket.repository.member.MemberRepository;
import cocode.cocodeMarket.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
@Profile("local")
public class InitDB {

    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void initDB() {
        log.info("INIT_DB_ROLE");
        initTestAdmin();
        initTestMember();
    }
    private void initTestAdmin() {
        Optional<Member> findMember = memberRepository.findByEmail("admin@gmail.com");
        if(findMember.isEmpty()){

        memberRepository.save(
                new Member("admin@gmail.com", passwordEncoder.encode("cocode!!^^J"),"admin","admin",
                        List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new),
                                roleRepository.findByRoleType(RoleType.ROLE_ADMIN).orElseThrow(RoleNotFoundException::new)))
        );
        }
    }

    private void initTestMember() {
        Optional<Member> findMember1 = memberRepository.findByEmail("initMember1@member.com");
        Optional<Member> findMember2 = memberRepository.findByEmail("initMember2@member.com");

        if (findMember1.isEmpty() || findMember2.isEmpty()) {
            memberRepository.saveAll(
                    List.of(
                            new Member("initMember1@member.com", passwordEncoder.encode("cocode!!^^J"), "member1", "member1",
                                    List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new))),
                            new Member("initMember2@member.com", passwordEncoder.encode("cocode!!^^J"), "member2", "member2",
                                    List.of(roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new))))
            );
        }
    }

}
