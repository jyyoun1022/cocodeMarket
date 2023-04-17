package cocode.cocodeMarket.dto.sign;

import cocode.cocodeMarket.entity.member.Member;
import cocode.cocodeMarket.entity.member.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @Email(message = "이메일 형식을 맞춰주세요!")
    @NotBlank(message = "이메일은 필수 값입니다.")
    private String email; // 1

    @NotBlank(message = "비밀번호는 필수 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "사용자 이름은 필수 값입니다.")
    @Size(min = 2,message = "사용자 이름은 최소 2글자 이상 입니다.")
    @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "사용자 이름은 한글 또는 알파벳만 입력해주세요.")
    private String username;

    @NotBlank(message = "닉네임은 필수 값입니다.")
    @Size(min = 4, message = "닉네임은 최소 4글자 이상 입니다.")
    @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "닉네임은 한글 또는 알파벳만 입력해주세요.")
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
