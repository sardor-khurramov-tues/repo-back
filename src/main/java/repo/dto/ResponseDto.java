package repo.dto;

import repo.constant.ResponseType;

public record ResponseDto<T>(
        Integer code,
        String message,
        T payload
) {

    public static <T> ResponseDto<T> getInstance(ResponseType type, T payload) {
        return new ResponseDto<>(type.getCode(), type.getMessage(), payload);
    }

    public static <T> ResponseDto<T> getSuccess(T payload) {
        return getInstance(ResponseType.SUCCESS, payload);
    }

    public static ResponseDto<Object> getEmptySuccess() {
        return getSuccess(null);
    }

}
