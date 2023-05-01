package cocode.cocodeMarket.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private String type;
    private PrincipalUserDetails principal;

    public CustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String type, PrincipalUserDetails principal) {
        super(authorities);
        this.type = type;
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

//    public String getType() {
//        return type;
//    }
}
