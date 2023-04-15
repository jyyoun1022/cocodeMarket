package cocode.cocodeMarket.service.sign;

import cocode.cocodeMarket.handler.JwtHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @InjectMocks
    TokenService tokenService;
    @Mock
    JwtHandler jwtHandler;

    @BeforeEach
    void init(){
        ReflectionTestUtils.setField(tokenService,"accessTokenMaxUseSeconds",10L);
        ReflectionTestUtils.setField(tokenService,"refreshTokenMaxUseSeconds",10L);
        ReflectionTestUtils.setField(tokenService,"accessKey","access");
        ReflectionTestUtils.setField(tokenService,"refreshKey","refresh");
    }

    @Test
    void createAccessTokenTest() {
        // given
        given(jwtHandler.createToken(anyString(),anyString(),anyLong())).willReturn("access");
        // when
        String accessToken = tokenService.createAccessToken("memberId");
        // then
        assertThat(accessToken).isEqualTo("access");
        verify(jwtHandler).createToken(anyString(),anyString(),anyLong());
    }
    @Test
    void createRefreshTokenTest() {
        // given
        given(jwtHandler.createToken(anyString(),anyString(),anyLong())).willReturn("refresh");
        // when
        String refreshToken = tokenService.createRefreshToken("memberId");
        // then
        assertThat(refreshToken).isEqualTo("refresh");
        verify(jwtHandler).createToken(anyString(),anyString(),anyLong());
    }
}
