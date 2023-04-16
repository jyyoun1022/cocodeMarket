package cocode.cocodeMarket.controller.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Success<T> implements Result {
    private T data;
}
