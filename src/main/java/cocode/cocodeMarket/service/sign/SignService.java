package cocode.cocodeMarket.service.sign;

import cocode.cocodeMarket.dto.sign.RefreshTokenResponse;
import cocode.cocodeMarket.dto.sign.SignInRequest;
import cocode.cocodeMarket.dto.sign.SignInResponse;
import cocode.cocodeMarket.dto.sign.SignUpRequest;
import cocode.cocodeMarket.entity.member.Member;
import cocode.cocodeMarket.entity.member.RoleType;
import cocode.cocodeMarket.exception.*;
import cocode.cocodeMarket.repository.member.MemberRepository;
import cocode.cocodeMarket.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    @Transactional
    public void signUp(SignUpRequest req) {
        validateSignUpInfo(req);
        memberRepository.save(SignUpRequest.toEntity(req,
                roleRepository.findByRoleType(RoleType.ROLE_NORMAL).orElseThrow(RoleNotFoundException::new),
                passwordEncoder));
    }

    public SignInResponse signIn(SignInRequest req) {
        Member member = memberRepository.findByEmail(req.getEmail()).orElseThrow(LoginFailureException::new);
        validatePassword(req, member);
        String subject = createSubject(member);
        String accessToken = tokenService.createAccessToken(subject);
        String refreshToken = tokenService.createRefreshToken(subject);
        return new SignInResponse(accessToken, refreshToken);
    }

    public RefreshTokenResponse refreshToken(String reToken) {
        validateRefreshToken(reToken);
        String subject = tokenService.extractRefreshTokenSubject(reToken);
        String accessToken = tokenService.createAccessToken(subject);
        return new RefreshTokenResponse(accessToken);
    }
    private void validateRefreshToken(String reToken) {
        if (!tokenService.validationRefreshToken(reToken)) {
            throw new AuthenticationEntryPointException();
        }
    }

    private void validateSignUpInfo(SignUpRequest req) {
        if(memberRepository.existsByEmail(req.getEmail()))
            throw new MemberEmailAlreadyExistsException(req.getEmail());
        if(memberRepository.existsByNickname(req.getNickname()))
            throw new MemberNicknameAlreadyExistsException(req.getNickname());
    }

    private void validatePassword(SignInRequest req, Member member) {
        if(!passwordEncoder.matches(req.getPassword(), member.getPassword())) {
            throw new LoginFailureException();
        }
    }
    private String createSubject(Member member) {
        return String.valueOf(member.getId());
    }

}
