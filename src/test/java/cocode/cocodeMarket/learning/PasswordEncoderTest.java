package cocode.cocodeMarket.learning;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PasswordEncoderTest {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void encodeWithBcryptTest() { // 2
        // given
        String password = "password";

        // when
        String encodedPassword = passwordEncoder.encode(password);

        // then
        assertThat(encodedPassword).contains("$2a$10$");
    }

    @Test
    void matchTest() { // 3
        // given
        String password = "password";
        String encodedPassword = passwordEncoder.encode(password);

        // when
        boolean isMatch = passwordEncoder.matches(password, encodedPassword);

        // then
        assertThat(isMatch).isTrue();
    }
}
