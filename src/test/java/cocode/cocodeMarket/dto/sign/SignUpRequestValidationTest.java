package cocode.cocodeMarket.dto.sign;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

public class SignUpRequestValidationTest {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validateTest() {
        // given
        SignUpRequest req = createSignUp();

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isEmpty();
    }

    @Test
    void invalidateByNotFormattedEmailTest() {
        // given
        String email = "email";
        SignUpRequest req = createSignUpWithEmail(email);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue()).collect(toSet()))
                .contains(email);
    }

    @Test
    void invalidateByEmptyEmailTest() {
        // given
        String email = null;
        SignUpRequest req = createSignUpWithEmail(email);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(toSet())).contains(email);
    }

    @Test
    void invalidateByBlankEmailTest() {
        // given
        String email = "       ";
        SignUpRequest req = createSignUpWithEmail(email);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(toSet())).contains(email);
    }

    @Test
    void invalidateByEmptyPasswordTest() {
        // given
        String password = null;
        SignUpRequest req = createSignUpWithPassword(password);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue()).collect(toSet()))
                .contains(password);
    }

    @Test
    void invalidateByBlankPasswordTest() {
        // given
        String password = "        ";
        SignUpRequest req = createSignUpWithPassword(password);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(toSet())).contains(password);
    }

    @Test
    void invalidateByShortPasswordTest() {
        // given
        String password = "coco!";
        SignUpRequest req = createSignUpWithPassword(password);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(toSet())).contains(password);
    }

    @Test
    void invalidateByNoneAlphabetPasswordTest() {
        // given
        String password = "123456!";
        SignUpRequest req = createSignUpWithPassword(password);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(toSet())).contains(password);
    }

    @Test
    void invalidateByNoneNumberPasswordTest() {
        // given
        String password = "co!!co!!";
        SignUpRequest req = createSignUpWithPassword(password);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(toSet())).contains(password);
    }

    @Test
    void invalidateByNoneSpecialCasePasswordTest() {
        // given
        String password = "coco1234567";
        SignUpRequest req = createSignUpWithPassword(password);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(toSet())).contains(password);
    }

    @Test
    void invalidateByEmptyUsernameTest() {
        // given
        String username = null;
        SignUpRequest req = createSignUpWithUsername(username);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(toSet())).contains(username);
    }

    @Test
    void invalidateByBlankUsernameTest() {
        // given
        String username = "      ";
        SignUpRequest req = createSignUpWithUsername(username);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(toSet())).contains(username);
    }

    @Test
    void invalidateByShortUsernameTest() {
        // given
        String username = "c";
        SignUpRequest req = createSignUpWithUsername(username);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(toSet())).contains(username);
    }

    @Test
    void invalidateByNotAlphabetOrKoreanUsernameTest() {
        // given
        String username = "Coco2";
        SignUpRequest req = createSignUpWithUsername(username);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(toSet())).contains(username);
    }

    @Test
    void invalidateByEmptyNicknameTest() {
        // given
        String username = null;
        SignUpRequest req = createSignUpWithUsername(username);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(toSet())).contains(username);
    }

    @Test
    void invalidateByBlankNicknameTest() {
        // given
        String username = "             ";
        SignUpRequest req = createSignUpWithUsername(username);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(toSet())).contains(username);
    }

    @Test
    void invalidateByShortNicknameTest() {
        // given
        String nickname = "co";
        SignUpRequest req = createSignUpWithNickname(nickname);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(toSet())).contains(nickname);
    }

    @Test
    void invalidateByNotAlphabetOrKoreanNicknameTest() {
        // given
        String nickname = "Coco재2";
        SignUpRequest req = createSignUpWithNickname(nickname);

        // when
        Set<ConstraintViolation<SignUpRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(toSet())).contains(nickname);
    }


    private SignUpRequest createSignUp() {
        return new SignUpRequest("test@gmail.com","coco!!1234","username","nickname");
    }
    private SignUpRequest createSignUpWithEmail(String email) {
        return new SignUpRequest(email,"coco!!1234","username","nickname");
    }
    private SignUpRequest createSignUpWithPassword(String password) {
        return new SignUpRequest("test@email.com", password, "username", "nickname");
    }
    private SignUpRequest createSignUpWithUsername(String username) {
        return new SignUpRequest("test@gmail.com","coco!!1234",username,"nickname");
    }
    private SignUpRequest createSignUpWithNickname(String nickname) {
        return new SignUpRequest("test@gmail.com","coco!!1234","username",nickname);
    }

}
