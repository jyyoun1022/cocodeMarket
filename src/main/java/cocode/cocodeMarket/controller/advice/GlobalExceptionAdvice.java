package cocode.cocodeMarket.controller.advice;

import cocode.cocodeMarket.dto.response.CustomResponse;
import cocode.cocodeMarket.exception.LoginFailureException;
import cocode.cocodeMarket.exception.MemberEmailAlreadyExistsException;
import cocode.cocodeMarket.exception.MemberNicknameAlreadyExistsException;
import cocode.cocodeMarket.exception.RoleNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500Error
    public CustomResponse exception(Exception e) { // 1
        log.info("e ::::: {} ",e.getMessage());
        return CustomResponse.failure(500,"FIND_ERROR_MESSAGE");
    }

    @ExceptionHandler(LoginFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse loginFailureException() {
        return CustomResponse.failure(404,"FAIL_LOGIN");
    }

    @ExceptionHandler(MemberEmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT) //충돌
    public CustomResponse memberEmailAlreadyExistsException(MemberNicknameAlreadyExistsException e) {
        return CustomResponse.failure(409,"CONFLICT_CAUSE_DUPLICATE_EMAIL");
    }

    @ExceptionHandler(MemberNicknameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomResponse memberNotFoundException() {
        return CustomResponse.failure(404,"MEMBER_IS_NOT_EXIST");
    }
    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomResponse roleNotFoundException() {
        return CustomResponse.failure(404,"ROLE_IS_NOT_EXIST");
    }
}
