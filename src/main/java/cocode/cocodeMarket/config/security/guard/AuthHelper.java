package cocode.cocodeMarket.config.security.guard;

import cocode.cocodeMarket.config.security.CustomAuthenticationToken;
import cocode.cocodeMarket.config.security.PrincipalUserDetails;
import cocode.cocodeMarket.entity.member.RoleType;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Log4j2
public class AuthHelper {

    public boolean isAuthenticated() {
        return getAuthentication() instanceof CustomAuthenticationToken;
    }
    public Long extractMemberId() {
        return Long.valueOf(getUserDetails().getUserId());
    }
    public Set<RoleType> extractMemberRoles() {
        return getUserDetails().getAuthorities()
                .stream().map(authority -> authority.getAuthority())
                .map(strAuth -> RoleType.valueOf(strAuth))
                .collect(Collectors.toSet());
    }

//    public boolean isAccessTokenType() {
//        return "access".equals(((CustomAuthenticationToken)getAuthentication()).getType());
//    }
//    public boolean isRefreshTokenType() {
//        return "refresh".equals(((CustomAuthenticationToken)getAuthentication()).getType());
//    }

    public PrincipalUserDetails getUserDetails() {
        return (PrincipalUserDetails)getAuthentication().getPrincipal();
    }
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
