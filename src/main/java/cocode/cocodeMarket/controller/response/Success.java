package cocode.cocodeMarket.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Success<T> implements Result {
    private T data;
}
