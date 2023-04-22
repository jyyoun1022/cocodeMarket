package cocode.cocodeMarket.service.sign;

import cocode.cocodeMarket.handler.JwtHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtHandler jwtHandler;

    @Value("${jwt.max-use.access}")
    private long accessTokenMaxUseSeconds;
    @Value("${jwt.max-use.refresh}")
    private long refreshTokenMaxUseSeconds;
    @Value("${jwt.key.access}")
    private String accessKey;
    @Value("${jwt.key.refresh}")
    private String refreshKey;

    public String createAccessToken(String subject) {
        return jwtHandler.createToken(accessKey,subject,accessTokenMaxUseSeconds);
    }
    public String createRefreshToken(String subject) {
        return jwtHandler.createToken(refreshKey,subject,refreshTokenMaxUseSeconds);
    }
    public boolean validationAccessToken(String token) {
        return jwtHandler.validate(accessKey,token);
    }
    public boolean validationRefreshToken(String token) {
        return jwtHandler.validate(refreshKey,token);
    }
    public String extractAccessTokenSubject(String token) {
        return jwtHandler.extractSubject(accessKey,token);
    }
    public String extractRefreshTokenSubject(String token) {
        return jwtHandler.extractSubject(refreshKey,token);
    }


}
