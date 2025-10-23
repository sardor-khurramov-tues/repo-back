package repo.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ResponseType {
    SUCCESS(0, "success"),

    INTERNAL_SERVER_ERROR(1, "internal server error"),
    NO_RESOURCE_FOUND(2, "no resource found"),
    METHOD_ARGUMENT_NOT_GIVEN(3, "method argument not given"),
    METHOD_ARGUMENT_NOT_VALID(4, "method argument not valid"),
    REQUEST_BODY_IS_MISSING(5, "required request body is missing"),
    UPLOAD_SIZE_EXCEED(6, "max file size is 5 MB"),
    VALIDATION_ERROR(7, "validation error"),

    NO_DEPARTMENT_WITH_THIS_ID(1001, "no department with this id"),
    DEPARTMENT_DELETION_FORBIDDEN(1002, "department deletion forbidden"),

    WRONG_CREDENTIALS(2001, "wrong credentials"),
    NO_USER_WITH_THIS_USERNAME(2002, "there is no user with this username"),
    USERNAME_EXISTS(2003, "username exists"),
    HEMIS_ID_EXISTS(2004, "hemis id exists"),
    NO_USER_WITH_THIS_ID(2005, "there is no user with this id"),
    WRONG_PASSWORD(2006, "wrong password"),
    USER_NOT_AUTHOR(2007, "user not author"),
    FALSE_DEPARTMENT(2008, "false department"),
    USER_DELETION_FORBIDDEN(2009, "user deletion forbidden"),
    USER_BLOCKED(2010, "user blocked"),

    NO_DOCUMENT_WITH_THIS_ID(3001, "no document with this id"),
    DOCUMENT_DELETION_FORBIDDEN(3002, "document deletion forbidden"),
    DOCUMENT_IS_PUBLISHED(3003, "document is published"),
    WRONG_DOCUMENT_SUBMITTER(3004, "wrong document submitter"),
    CONTRIBUTOR_EXISTS(3005, "contributor exists"),
    NO_DOC_CONTRIBUTOR_WITH_THIS_ID(3006, "no doc contributor with this id"),
    WRONG_DOCUMENT_STAFF(3007, "wrong document staff"),
    SUBMITTER_DELETION_FORBIDDEN(3008, "submitter deletion forbidden"),
    MAX_NON_PUBLISHED_DOC_LIMIT_REACHED(3009, "max non published doc limit reached"),

    BAD_REQUEST(99999, "bad request");

    private final Integer code;
    private final String message;

    ResponseType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseType getTypeByCode(String codeAsString) {
        return Arrays.stream(ResponseType.values())
                .filter(type -> type.getCode()
                                .equals(Integer.valueOf(codeAsString)))
                .findFirst()
                .orElseThrow();
    }
}
