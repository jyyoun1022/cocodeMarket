package cocode.cocodeMarket.service.sign;

import cocode.cocodeMarket.dto.sign.RefreshTokenResponse;
import cocode.cocodeMarket.dto.sign.SignInRequest;
import cocode.cocodeMarket.dto.sign.SignInResponse;
import cocode.cocodeMarket.dto.sign.SignUpRequest;
import cocode.cocodeMarket.entity.member.Member;
import cocode.cocodeMarket.entity.member.Role;
import cocode.cocodeMarket.entity.member.RoleType;
import cocode.cocodeMarket.exception.*;
import cocode.cocodeMarket.repository.member.MemberRepository;
import cocode.cocodeMarket.repository.role.RoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SignServiceTest {
    @InjectMocks
    SignService signService;
    @Mock
    MemberRepository memberRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    TokenService tokenService;

    @Test
    void signUpTest() {
        // given
        SignUpRequest req = createSignUpRequest();
        given(roleRepository.findByRoleType(RoleType.ROLE_NORMAL)).willReturn(Optional.of(new Role(RoleType.ROLE_NORMAL)));
        // when
        signService.signUp(req);
        // then
        verify(passwordEncoder).encode(req.getPassword());
        verify(memberRepository).save(any());
    }
    @Test
    void validateSignUpByDuplicateEmailTest() {
        // given
        given(memberRepository.existsByEmail(anyString())).willReturn(true);
        // when, then
        assertThatThrownBy(() -> signService.signUp(createSignUpRequest()))
                .isInstanceOf(MemberEmailAlreadyExistsException.class);
    }

    @Test
    void validateSignUpDuplicateNicknameTest() {
       // given
       given(memberRepository.existsByNickname(anyString())).willReturn(true);
       // when, then
       assertThatThrownBy(() -> signService.signUp(createSignUpRequest()))
               .isInstanceOf(MemberNicknameAlreadyExistsException.class);
    }

    @Test
    void signupRoleNotFoundTest() {
        // given
        given(roleRepository.findByRoleType(RoleType.ROLE_NORMAL)).willReturn(Optional.empty());
        // when, then
        assertThatThrownBy(() -> signService.signUp(createSignUpRequest()))
                .isInstanceOf(RoleNotFoundException.class);
    }
    @Test
    void signInTest() {
        // given
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(createMember()));
        given(passwordEncoder.matches(anyString(),anyString())).willReturn(true);
        given(tokenService.createAccessToken(anyString())).willReturn("access");
        given(tokenService.createRefreshToken(anyString())).willReturn("refresh");
        // when
        SignInResponse res = signService.signIn(createSignInRequest());
        // then
        assertThat(res.getAccessToken()).isEqualTo("access");
        assertThat(res.getRefreshToken()).isEqualTo("refresh");
    }
    @Test
    void signInExceptionByNoneMemberTest() {
        // given
        given(memberRepository.findByEmail(any())).willReturn(Optional.empty());
        // when, then
        assertThatThrownBy(() -> signService.signIn(createSignInRequest()))
                .isInstanceOf(LoginFailureException.class);
    }
    @Test
    void signInExceptionByInvalidPasswordTest() {
        // given
        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(createMember()));
        given(passwordEncoder.matches(anyString(),anyString())).willReturn(false);
        // when, then
        assertThatThrownBy(() -> signService.signIn(createSignInRequest()))
                .isInstanceOf(LoginFailureException.class);
    }
    private SignInRequest createSignInRequest() {
        return new SignInRequest("test@gmail.com","1234");
    }
    private SignUpRequest createSignUpRequest() {
        return new SignUpRequest("test@gmail.com","1234","tester","testNick");
    }
    private Member createMember() {
        return new Member("test@gmail.com","1234","tester","testNick", Collections.emptyList());
    }

    @Test
    void refreshTokenTest() {
        // given
        String refreshToken = "refreshToken";
        String subject = "subject";
        String accessToken = "accessToken";
//        BDDMockito.given(tokenService.validationRefreshToken(refreshToken)).willReturn(true);
        BDDMockito.given(tokenService.extractRefreshTokenSubject(refreshToken)).willReturn(subject);
        BDDMockito.given(tokenService.createAccessToken(subject)).willReturn(accessToken);
        // when
        RefreshTokenResponse res = signService.refreshToken(refreshToken);
        // then
        Assertions.assertThat(res.getAccessToken()).isEqualTo(accessToken);
    }
    @Test
    void refreshTokenExceptionByInvalidTokenTest() {
        // given
        String refreshToken = "refreshToken";
        BDDMockito.given(tokenService.validationRefreshToken(refreshToken)).willReturn(false);
        // when, then
        Assertions.assertThatThrownBy(() -> signService.refreshToken(refreshToken))
                .isInstanceOf(AuthenticationEntryPointException.class);
    }

}
