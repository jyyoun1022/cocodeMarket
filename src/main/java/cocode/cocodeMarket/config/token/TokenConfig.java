package cocode.cocodeMarket.config.token;

import cocode.cocodeMarket.handler.JwtHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TokenConfig {

    private final JwtHandler jwtHandler;

    @Bean
    public TokenHelper accessTokenHelper(
            @Value("${jwt.key.access}")String key,
            @Value("${jwt.max-use.access}") long maxAgeSeconds
    ) {
       return new TokenHelper(jwtHandler,key,maxAgeSeconds);
    }
    @Bean TokenHelper refreshTokenHelper(
            @Value("${jwt.key.refresh}") String key,
            @Value("${jwt.max-use.refresh}") long maxAgeSeconds
    ) {
        return new TokenHelper(jwtHandler,key,maxAgeSeconds);

    }
}
