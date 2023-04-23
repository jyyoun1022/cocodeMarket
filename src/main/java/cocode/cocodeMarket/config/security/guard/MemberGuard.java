package cocode.cocodeMarket.config.security.guard;

import cocode.cocodeMarket.entity.member.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Log4j2
public class MemberGuard {

    private final AuthHelper authHelper;

    public boolean check(Long id) {
        return authHelper.isAuthenticated() && authHelper.isAccessTokenType() && hasAuthority(id);
    }

    public boolean hasAuthority(Long id) {
        Long memberId = authHelper.extractMemberId();
        Set<RoleType> memberRoles = authHelper.extractMemberRoles();
        return id.equals(memberId) || memberRoles.contains(RoleType.ROLE_ADMIN);
    }

}
