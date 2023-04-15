package cocode.cocodeMarket.dto.sign;

import cocode.cocodeMarket.entity.member.Member;
import cocode.cocodeMarket.entity.member.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Data
@AllArgsConstructor
public class SignUpRequest {
    private String email;
    private String password;
    private String username;
    private String nickname;

    public static Member toEntity(SignUpRequest request, Role role, PasswordEncoder encoder) {
        return Member.builder()
                .email(request.email)
                .username(request.username)
                .nickname(request.nickname)
                .password(encoder.encode(request.password))
                .roles(List.of(role))
                .build();
    }
}
