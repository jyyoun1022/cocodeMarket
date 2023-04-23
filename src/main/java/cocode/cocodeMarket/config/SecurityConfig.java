package cocode.cocodeMarket.config;

import cocode.cocodeMarket.config.security.CustomAccessDeniedHandler;
import cocode.cocodeMarket.config.security.CustomAuthenticationEntryPoint;
import cocode.cocodeMarket.config.security.JwtAuthenticationFilter;
import cocode.cocodeMarket.config.security.PrincipalUserDetailsService;
import cocode.cocodeMarket.service.sign.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity //EnableWebSecurity 안에 @Configuration이 있기때문에 생략
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService; // 1
    private final PrincipalUserDetailsService principalUserDetailsService; // 2

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/exception/**"); //3
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests() // 4
                    .antMatchers(HttpMethod.POST,"/api/sign-in","/api/sign-up").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/**").permitAll()
                    .antMatchers(HttpMethod.DELETE,"/api/members/{id}/**").access("@memberGuard.check(#id)")
                    .anyRequest().hasAnyRole("ADMIN")
                .and()
                    .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()) // 5
                .and()
                    .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 6
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(tokenService,principalUserDetailsService),
                            UsernamePasswordAuthenticationFilter.class); // 7
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
