package cocode.cocodeMarket.learning;

import cocode.cocodeMarket.handler.JwtHandler;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtHandlerTest {
    JwtHandler jwtHandler = new JwtHandler();

    @Test
    void createTokenTest() {
        // given, when
        String encodedKey = Base64.getEncoder().encodeToString("privateKey".getBytes());
        String token = createToken(encodedKey, "subject", 60L);

        // then
        assertThat(token).contains("Bearer ");
    }

    @Test
    void extractSubjectTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("privateKey".getBytes());
        String subject = "subject";
        String token = createToken(encodedKey, subject, 60L);

        // when
        String extractedSubject = jwtHandler.extractSubject(encodedKey, token);

        // then
        assertThat(extractedSubject).isEqualTo(subject);
    }
    @Test
    void validateTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("privateKey".getBytes());
        String token = createToken(encodedKey, "subject", 60L);

        // when
        boolean isValid = jwtHandler.validate(encodedKey, token);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    void invalidateByInvalidKeyTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("privateKey".getBytes());
        String token = createToken(encodedKey, "subject", 60L);

        // when
        boolean isValid = jwtHandler.validate("invalid", token);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    void invalidateByExpiredTokenTest() {
        // given
        String encodedKey = Base64.getEncoder().encodeToString("privateKey".getBytes());
        String token = createToken(encodedKey, "subject", 0L);

        // when
        boolean isValid = jwtHandler.validate(encodedKey, token);

        // then
        assertThat(isValid).isFalse();
    }
    private String createToken(String encodedKey, String subject, long maxUseSeconds) {
        return jwtHandler.createToken(
                encodedKey,
                subject,
                maxUseSeconds);
    }
}
