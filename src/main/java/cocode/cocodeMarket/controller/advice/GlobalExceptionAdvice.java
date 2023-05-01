package cocode.cocodeMarket.controller.advice;

import cocode.cocodeMarket.dto.response.CustomResponse;
import cocode.cocodeMarket.exception.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500Error
    public CustomResponse exception(Exception e) { // 1
        log.info("e:::::::>>> {}",e.toString());
        log.info("e ::::: {} ",e.getMessage());
        return CustomResponse.failure(500,"FIND_ERROR_MESSAGE");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse processValidationError(MethodArgumentNotValidException e) {
        log.info("VALIDATION_CHECK");
        return CustomResponse.failure(404,e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(LoginFailureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse loginFailureException() {
        return CustomResponse.failure(404,"FAIL_LOGIN");
    }

    @ExceptionHandler(MemberEmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CustomResponse memberEmailAlreadyExistsException(MemberEmailAlreadyExistsException e) {
        return CustomResponse.failure(409,"::::::::::> "+e.getMessage() + "IS_DUPLICATE_EMAIL");
    }

    @ExceptionHandler(MemberNicknameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomResponse memberNicknameAlreadyExistsException(MemberNicknameAlreadyExistsException e) {
        return CustomResponse.failure(404,"::::::::::> " + e.getMessage() + "IS_DUPLICATE_NICKNAME");
    }

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomResponse memberNotFoundException(){
        return CustomResponse.failure(404,"::::::::::> MEMBER_IS_NOT_FOUND");
    }
    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomResponse roleNotFoundException() {
        return CustomResponse.failure(404,"ROLE_IS_NOT_EXIST");
    }
    @ExceptionHandler(AuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CustomResponse authenticationEntryPoint() {
        return CustomResponse.failure(401,"IS_NOT_AUTHENTICATION");
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CustomResponse accessDeniedException() {
        return CustomResponse.failure(403,"ACCESS_IS_DENIED");
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse missingRequestHeaderException(MissingRequestHeaderException e) {
        return CustomResponse.failure(404,e.getHeaderName() + "NEED_REQUEST_HEADER");
    }
}
