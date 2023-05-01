package cocode.cocodeMarket.controller.sign;

import cocode.cocodeMarket.dto.response.CustomResponse;
import cocode.cocodeMarket.dto.sign.SignInRequest;
import cocode.cocodeMarket.dto.sign.SignUpRequest;
import cocode.cocodeMarket.service.sign.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cocode.cocodeMarket.dto.response.CustomResponse.*;

@RestController // 1
@Log4j2
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;

    @PostMapping("/api/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse signUp(@Valid @RequestBody SignUpRequest req) { // 2
        signService.signUp(req);
        return success(); // static import 로 CustomResponse 생략가능
    }
    @PostMapping("/api/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse SignIn(@Valid @RequestBody SignInRequest req) { // 3
        return success(signService.signIn(req));
    }

    @PostMapping("/api/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse refreshToken(@RequestHeader(value = "Authorization") String refreshToken) {
        return success(signService.refreshToken(refreshToken));
    }
}
