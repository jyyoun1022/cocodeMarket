package cocode.cocodeMarket.config.security;

import cocode.cocodeMarket.service.sign.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends GenericFilter {

    private final TokenService tokenService;
    private final PrincipalUserDetailsService principalUserDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = extractToken((HttpServletRequest) request);
        if (validateAccessToken(token)) {
            setAccessAuthentication("access",token);
        } else if (validateRefreshToken(token)) {
            setRefreshAuthentication("refresh",token);
        }
        chain.doFilter(request,response);
    }

    private String extractToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
    private boolean validateAccessToken(String token) {
        return token != null && tokenService.validationAccessToken(token);
    }
    private boolean validateRefreshToken(String token) {
        return token != null && tokenService.validationRefreshToken(token);
    }
    private void setAccessAuthentication(String type, String token) {
        String userId = tokenService.extractAccessTokenSubject(token);
        PrincipalUserDetails userDetails = principalUserDetailsService.loadUserByUsername(userId);
        SecurityContextHolder.getContext().setAuthentication(new CustomAuthenticationToken(userDetails.getAuthorities(),type,userDetails));
    }
    private void setRefreshAuthentication(String type, String token) {
        String userId = tokenService.extractRefreshTokenSubject(token);
        PrincipalUserDetails userDetails = principalUserDetailsService.loadUserByUsername(userId);
        SecurityContextHolder.getContext().setAuthentication(new CustomAuthenticationToken(userDetails.getAuthorities(),type,userDetails));
    }
}
