package cocode.cocodeMarket.dto.sign;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SignInRequestValidationTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator(); // 4

    @Test
    void validateTest() {
        // given
        SignInRequest req = createSignIn();
        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req); // 5
        // then
        assertThat(validate).isEmpty(); // 6
    }

    @Test
    void inValidateByNotFormattedEmailTest() {
        // given
        String email = "test";
        SignInRequest req = createSignInWithEmail(email);
        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req);
        // then
        assertThat(validate).isNotEmpty(); // 7
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(Collectors.toSet())).contains(email); // 8
    }

    @Test
    void invalidateByEmptyEmailTest() {
        // given
        String email = "";
        SignInRequest req = createSignInWithEmail(email);
        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req);
        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(Collectors.toSet())).contains(email);
    }
    @Test
    void invalidateByEmptyPasswordTest() {
        // given
        String password = null;
        SignInRequest req = createSignInWithPassword(password);
        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req);
        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(Collectors.toSet())).contains(password);
    }
    @Test
    void invalidateByBlankPasswordTest() {
        // given
        String password = "";
        SignInRequest req = createSignInWithPassword(password);
        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req);
        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(i -> i.getInvalidValue())
                .collect(Collectors.toSet())).contains(password);
    }

    private SignInRequest createSignIn(){ // 1
        return new SignInRequest("test@gmail.com","coco!!1234");
    }
    private SignInRequest createSignInWithEmail(String email) { // 2
        return new SignInRequest(email,"coco!!1234");
    }
    private SignInRequest createSignInWithPassword(String password) { // 3
        return new SignInRequest("test@gmail.com",password);
    }

}
