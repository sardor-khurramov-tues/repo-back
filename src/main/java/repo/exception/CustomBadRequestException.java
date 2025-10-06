package repo.exception;

import repo.constant.ResponseType;

public class CustomBadRequestException extends RuntimeException {

    public CustomBadRequestException(ResponseType responseType) {
        super(responseType.getCode().toString());
    }

}
