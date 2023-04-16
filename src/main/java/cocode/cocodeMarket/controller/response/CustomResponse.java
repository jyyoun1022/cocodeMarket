package cocode.cocodeMarket.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL) // 1
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 2
@Getter
public class CustomResponse {

    private boolean success;
    private int code;
    private Result result;

    public static CustomResponse success() { // 3
        return new CustomResponse(true,200,null);
    }
    public static <T> CustomResponse success(T data) { // 4
        return new CustomResponse(true,200,new Success<>(data));
    }
    public static CustomResponse failure(int code, String msg) { // 5
        return new CustomResponse(false,code,new Failure(msg));
    }

}
